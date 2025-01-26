package com.example.to_docompose.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.Stats
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.data.repositories.DataStoreRepository
import com.example.to_docompose.data.repositories.ToDoRepository
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.MAX_TITLE_LENGTH
import com.example.to_docompose.util.RequestState
import com.example.to_docompose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    // XP and Level Attributes
    private val _currentXP = MutableStateFlow(0)
    val currentXP: StateFlow<Int> = _currentXP

    private val _currentLevel = MutableStateFlow(1)
    val currentLevel: StateFlow<Int> = _currentLevel

    private val _progressBar = MutableStateFlow(0f)
    val progressBar: StateFlow<Float> = _progressBar

    val xpForNextLevel: StateFlow<Int>
        get() = _currentLevel.map { it * XP_PER_LEVEL }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = XP_PER_LEVEL
        )



    companion object {
        private const val XP_PER_LEVEL = 10
        private const val MAX_LEVEL = 20
        private const val MAX_QUICKBOARD_TASKS = 3
        private const val MAX_STATS_PER_ATTRIBUTE = 6
    }


    // Task Management Attributes
    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _quickBoardTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val quickBoardTasks: StateFlow<List<ToDoTask>> = _quickBoardTasks

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    val lowPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val highPriorityTasks: StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    var action by mutableStateOf(Action.NO_ACTION)
        private set

    var id by mutableIntStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set
    var priority by mutableStateOf(Priority.LOW)
        private set

    var searchAppBarState by mutableStateOf(SearchAppBarState.CLOSED)
        private set
    var searchTextState by mutableStateOf("")
        private set

    private var expPointsEarned by mutableIntStateOf(0)

    // Initialization
    init {
        getAllTasks()
        updateQuickBoardTasks()
        readSortState()
        loadXPAndLevel()
        loadStats()
    }

    // XP and Level Handling
    private fun loadXPAndLevel() {
        viewModelScope.launch {
            dataStoreRepository.readXPAndLevel
                .collect { (xp, level) ->
                    _currentXP.value = xp
                    _currentLevel.value = level.coerceAtLeast(1)
                    updateProgressBar()
                }
        }
    }

    private fun saveXPAndLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveXPAndLevel(_currentXP.value, _currentLevel.value)
        }
    }

    fun completeTask(task: ToDoTask) {
        val xpGained = when (task.priority) {
            Priority.LOW -> 1
            Priority.MEDIUM -> 5
            Priority.HIGH -> 10
            else -> 0
        }
        _currentXP.value += xpGained
        expPointsEarned = xpGained

        while (_currentXP.value >= XP_PER_LEVEL * _currentLevel.value && _currentLevel.value < MAX_LEVEL) {
            _currentXP.value -= XP_PER_LEVEL * _currentLevel.value
            _currentLevel.value += 1

            // Update stat points in _stats
            _stats.value = _stats.value.copy(
                statPoints = _stats.value.statPoints + 1
            )
        }
        // Save updates
        updateProgressBar()
        saveXPAndLevel()
        saveStats(_stats.value)
        loadStats()

        // Trigger snackbar notification
        action = Action.COMPLETE_TASK

        // Delete the task
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    private fun updateProgressBar() {
        _progressBar.value = _currentXP.value.toFloat() / (XP_PER_LEVEL * _currentLevel.value)
    }


    //*******STATS STUFF*************

    // Declare MutableStateFlow for Stats
    private val _stats = MutableStateFlow(
        Stats(
            discipline = 0,
            productivity = 0,
            energy = 0,
            statPoints = 0,
        )
    )

    // Stats Handling
    private fun loadStats() {
        viewModelScope.launch {
            dataStoreRepository.readStats.collect { stats ->
                _stats.value = stats
            }
        }
    }

    private fun saveStats(updatedStats: Stats) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveStats(
                discipline = updatedStats.discipline,
                productivity = updatedStats.productivity,
                energy = updatedStats.energy,
                statPoints = updatedStats.statPoints
            )
        }
    }

    fun upgradeStat(stat: String) {
        if (_stats.value.statPoints > 0) {
            val updatedStats = when (stat) {
                "discipline" -> _stats.value.copy(
                    discipline = (_stats.value.discipline + 1).coerceAtMost(MAX_STATS_PER_ATTRIBUTE),
                    statPoints = (_stats.value.statPoints - 1).coerceAtLeast(0)
                )
                "productivity" -> _stats.value.copy(
                    productivity = (_stats.value.productivity + 1).coerceAtMost(MAX_STATS_PER_ATTRIBUTE),
                    statPoints = (_stats.value.statPoints - 1).coerceAtLeast(0)
                )
                "energy" -> _stats.value.copy(
                    energy = (_stats.value.energy + 1).coerceAtMost(MAX_STATS_PER_ATTRIBUTE),
                    statPoints = (_stats.value.statPoints - 1).coerceAtLeast(0)
                )
                else -> _stats.value
            }

            _stats.value = updatedStats
            saveStats(updatedStats)
        }
    }

    val stats: StateFlow<Stats> = _stats

    //****STATS STUFF END****


    //+++++AVATAR STUFF+++++
    private val _selectedAvatar = MutableStateFlow(R.drawable.pirate) // Default avatar
    val selectedAvatar: StateFlow<Int> = _selectedAvatar

    init {
        loadSelectedAvatar()
    }

    fun saveSelectedAvatar(avatar: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSelectedAvatar(avatar)
            _selectedAvatar.value = avatar
        }
    }

    private fun loadSelectedAvatar() {
        viewModelScope.launch {
            dataStoreRepository.readSelectedAvatar.collect { avatar ->
                _selectedAvatar.value = avatar
            }
        }
    }


    // Quick Board Management
    private fun updateQuickBoardTasks() {
        viewModelScope.launch {
            repository.getQuickBoardTasks.collect { tasks ->
                _quickBoardTasks.value = tasks.take(MAX_QUICKBOARD_TASKS) // Limit to 3 tasks
            }
        }
    }

    fun toggleQuickBoardStatus(task: ToDoTask) {
        viewModelScope.launch {
            val currentQuickBoardTasks = _quickBoardTasks.value

            // Ensure no duplicates and respect the max limit
            if (task.isQuickBoard || currentQuickBoardTasks.size < MAX_QUICKBOARD_TASKS) {
                repository.markTaskForQuickBoard(task.id, !task.isQuickBoard)

                // Update the QuickBoard tasks state
                updateQuickBoardTasks()
            }
        }
    }

    // Task Management
    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        viewModelScope.launch {
            repository.getAllTasks.collect { tasks ->
                _allTasks.value = RequestState.Success(tasks)
            }
        }
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        viewModelScope.launch {
            try {
                repository.searchDatabase("%$searchQuery%").collect { tasks ->
                    _searchedTasks.value = RequestState.Success(tasks)
                }
            } catch (e: Exception) {
                _searchedTasks.value = RequestState.Error(e)
            }
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
    }

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        viewModelScope.launch {
            try {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect { priority ->
                        _sortState.value = RequestState.Success(priority)
                    }
            } catch (e: Exception) {
                _sortState.value = RequestState.Error(e)
            }
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority)
        }
    }

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    // Database Actions
    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
                updateAction(Action.NO_ACTION)
            }
            Action.UPDATE -> {
                updateTask()
            }
            Action.DELETE -> {
                deleteTask()
            }
            Action.DELETE_ALL -> {
                deleteAllTasks()
            }
            Action.UNDO -> {
                undoTask()
            }
            Action.COMPLETE_TASK -> {
                // Task completion logic should already handle this
            }
            else -> {}
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(toDoTask)
        }
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedTask.value?.let { task ->
                repository.updateTask(task.copy(title = title, description = description, priority = priority))
            }
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedTask.value?.let { repository.deleteTask(it) }
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    private fun undoTask() {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedTask.value?.let { repository.addTask(it) }
        }
    }

    // Task Field Updates
    fun updateTaskFields(selectedTask: ToDoTask?) {
        if (selectedTask != null) {
            id = selectedTask.id
            title = selectedTask.title
            description = selectedTask.description
            priority = selectedTask.priority
        } else {
            id = 0
            title = ""
            description = ""
            priority = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String) {
        if (newTitle.length <= MAX_TITLE_LENGTH) title = newTitle
    }

    fun updateDescription(newDescription: String) {
        description = newDescription
    }

    fun updatePriority(newPriority: Priority) {
        priority = newPriority
    }

    fun updateAction(newAction: Action) {
        action = newAction
    }

    fun updateAppBarState(newState: SearchAppBarState) {
        searchAppBarState = newState
    }

    fun updateSearchText(newText: String) {
        searchTextState = newText
    }

    fun validateFields(): Boolean {
        return title.isNotEmpty() && description.isNotEmpty()
    }
}