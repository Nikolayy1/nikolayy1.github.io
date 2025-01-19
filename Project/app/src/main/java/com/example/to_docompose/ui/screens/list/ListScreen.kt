package com.example.to_docompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.Custom_beige
import com.example.to_docompose.ui.theme.Custom_orange
import com.example.to_docompose.ui.theme.Custom_white
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.SearchAppBarState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun ListScreen(
    action: Action,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState: String = sharedViewModel.searchTextState

    val snackBarHostState = remember { SnackbarHostState() }
    val expPoints = sharedViewModel.expPointsEarned

    // Display the snackbar for task completion
    DisplaySnackBar(
        snackBarHostState = snackBarHostState,
        expPoints = expPoints,
        action = action,
        sharedViewModel = sharedViewModel
    )

    Scaffold(
        modifier = Modifier.background(Custom_beige),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                navigateToMainScreen = navigateToMainScreen
            )
        },
        content = { padding ->
            ListContent(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        containerColor = Custom_orange,
        contentColor = Custom_white
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = R.string.add_button
            )
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackBarHostState: SnackbarHostState,
    expPoints: Int,
    action: Action,
    sharedViewModel: SharedViewModel
) {
    // List of dynamic completion messages
    val completionMessages = listOf(
        "Effort brings growth. +%d EXP.",
        "Progress is forged in action. +%d EXP.",
        "Your diligence pays off with +%d EXP.",
        "Each quest strengthens your resolve. +%d EXP.",
        "A step forward, a reward of +%d EXP.",
        "Completion builds mastery. +%d EXP.",
        "Victory rewards you with +%d EXP.",
        "With effort comes reward: +%d EXP.",
        "Your journey advances. +%d EXP.",
        "Another quest, another gain: +%d EXP.",
        "That quest didn’t stand a chance. +%d EXP!",
        "Boom! Another one bites the dust. +%d EXP.",
        "Somewhere, the procrastination gods weep. +%d EXP.",
        "Hasta la vista, quest. +%d EXP.",
        "Do. Or do not. There is no try. +%d EXP.",
        "Victory achieved. Quest complete. +%d EXP.",
        "The cake was a lie, but the quest wasn’t. +%d EXP.",
        "You’ve entered God Mode of quest completion. +%d EXP.",
        "From zero to hero: Quest completed! +%d EXP.",
        "Another quest bites the dust. Fatality! +%d EXP.",
        "Round 1: Quest. Round 2: Complete. Fight Over. +%d EXP.",
        "Quest annihilated! Fatality! +%d EXP.",
        "The harder the fight, the sweeter the victory. +%d EXP.",
        "Seize the day. +%d EXP.",
        "Courage conquers all things. +%d EXP.",
        "Strength lies in overcoming. +%d EXP.",
        "Action is the foundation of success. +%d EXP.",
        "Obstacle or stepping stone, you decide. +%d EXP.",
        "The mountain is climbed one step at a time. +%d EXP.",
        "Flawless performance. +%d EXP.",
        "Your to-do list is begging for mercy. Mercy denied! +%d EXP.",
        "Step by step, you carve your own legend. +%d EXP.",
        "Quest neutralized. +%d EXP.",
        "System update: Quest complete. Efficiency increased. +%d EXP.",
        "+%d EXP earned in glory.",
        "Virtue is forged through action. +%d EXP.",
        "Strength lies in perseverance. +%d EXP.",
        "The reward is in the journey. +%d EXP.",
        "Look at you, all productive and stuff. +%d EXP.",
        "Mastery is born of discipline. +%d EXP.",
        "A hero's path is paved in quests. +%d EXP.",
        "No wind favors the idle. +%d EXP.",
        "Victory belongs to the persistent. +%d EXP.",
        "Glory is earned, not given. +%d EXP.",
        "Discipline breeds greatness. +%d EXP.",
        "Through struggle, we rise. +%d EXP.",
        "Completion brings wisdom. +%d EXP.",
        "Action is the beginning of change. +%d EXP.",
        "Even a small effort leads to great gains. +%d EXP.",
        "Every completed quest is a stone laid on the path to mastery. +%d EXP.",
        "The greatest treasures lie at the end of the hardest quests. +%d EXP.",
        "A calm mind and steady hand conquers the fiercest quests. +%d EXP.",
        "Through the ashes, you rise triumphant. +%d EXP.",
        "The quest is complete. You have done well. +%d EXP.",
        "Impressive. Most impressive. +%d EXP.",
        "The force is strong with you. +%d EXP.",
        "In every quest lies a lesson. +%d EXP.",
    )

    // Function to randomly select a message
    fun getRandomCompletionMessage(expPoints: Int): String {
        return completionMessages.random().format(expPoints)
    }

    LaunchedEffect(key1 = action) {
        if (action == Action.COMPLETE_TASK) {
            // Use a random message
            val message = getRandomCompletionMessage(expPoints)
            val result = snackBarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
            if (result == SnackbarResult.Dismissed) {
                sharedViewModel.updateAction(Action.NO_ACTION) // Reset action after snackbar
            }
        }
    }
}

