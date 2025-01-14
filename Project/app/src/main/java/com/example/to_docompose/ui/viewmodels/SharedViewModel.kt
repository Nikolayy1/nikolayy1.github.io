package com.example.to_docompose.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.Priority
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
    private var currentXP by mutableIntStateOf(0)
    var currentLevel by mutableIntStateOf(1)
    var progressBar by mutableStateOf(0f)

    private val xpPerLevel = 10 // XP required for level 1, 20 for level 2, etc.
    private val maxLevel = 20

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

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _sortState =
        MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    init {
        getAllTasks()
        readSortState()
        loadXPAndLevel()
    }

    // Load XP and Level from DataStore
    private fun loadXPAndLevel() {
        viewModelScope.launch {
            dataStoreRepository.readXPAndLevel
                .collect { (xp, level) ->
                    currentXP = xp
                    currentLevel = level
                    updateProgressBar()
                }
        }
    }

    // Save XP and Level to DataStore
    private fun saveXPAndLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveXPAndLevel(currentXP, currentLevel)
        }
    }

    // Handle Task Completion and XP Gain
    fun completeTask(task: ToDoTask) {
        val xpGained = when (task.priority) {
            Priority.LOW -> 1
            Priority.MEDIUM -> 5
            Priority.HIGH -> 10
            else -> 0
        }

        currentXP += xpGained
        while (currentXP >= xpPerLevel * currentLevel && currentLevel < maxLevel) {
            currentXP -= xpPerLevel * currentLevel
            currentLevel++
        }
        updateProgressBar()
        saveXPAndLevel()
    }

    // Update Progress Bar
    private fun updateProgressBar() {
        progressBar = currentXP.toFloat() / (xpPerLevel * currentLevel)
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState = SearchAppBarState.TRIGGERED
    }

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

    private fun readSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }
                    .collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    private fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = title,
                description = description,
                priority = priority
            )
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState = SearchAppBarState.CLOSED
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.updateTask(toDoTask = toDoTask)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id,
                title = title,
                description = description,
                priority = priority
            )
            repository.deleteTask(toDoTask = toDoTask)
        }
    }

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

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
                addTask()
                updateAction(Action.NO_ACTION)
            }
            else -> {

            }
        }
    }

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
        if (newTitle.length < MAX_TITLE_LENGTH) {
            title = newTitle
        }
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
