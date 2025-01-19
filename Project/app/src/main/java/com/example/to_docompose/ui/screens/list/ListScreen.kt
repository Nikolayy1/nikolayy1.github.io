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
        "Each task strengthens your resolve. +%d EXP.",
        "A step forward, a reward of +%d EXP.",
        "Completion builds mastery. +%d EXP.",
        "Victory rewards you with +%d EXP.",
        "With effort comes reward: +%d EXP.",
        "Your journey advances. +%d EXP.",
        "Another task, another gain: +%d EXP.",
        "Quest complete! Your to-do list trembles in fear.",
        "That task didn’t stand a chance. +%d EXP!",
        "Boom! Another one bites the dust. +%d EXP.",
        "Somewhere, the procrastination gods weep. +%d EXP.",
        "Finally, that task won't be haunting you anymore. +%d EXP.",
        "You slayed that task like a boss. Your to-do list cowers. +%d EXP.",
        "One task down, now back to pretending you’re busy. +%d EXP.",
        "That task was no match for your barely contained determination. +%d EXP.",
        "Hasta la vista, task. +%d EXP.",
        "Do. Or do not. There is no try. +%d EXP.",
        "Victory achieved. Task complete. +%d EXP.",
        "The cake was a lie, but the task wasn’t. +%d EXP.",
        "You’ve entered God Mode of task completion. +%d EXP.",
        "From zero to hero: Task completed! +%d EXP.",
        "Another task bites the dust. Fatality! +%d EXP.",
        "Round 1: Task. Round 2: Complete. Fight Over. +%d EXP.",
        "Task annihilated! Fatality! +%d EXP.",
        "Your task has been finished. Flawless performance. +%d EXP.",
        "Your to-do list is begging for mercy. Mercy denied! +%d EXP.",
        "The Productivity Gods are proud of you. Another task down. +%d EXP.",
        "Step by step, you carve your own legend. +%d EXP.",
        "Task neutralized. You’re one step closer to greatness. +%d EXP.",
        "System update: Task complete. Efficiency increased. +%d EXP.",
        "Congrats, you’re officially better than average. +%d EXP.",
        "Your victory echoes through the ages. +%d EXP earned in glory.",
        "Look at you, all productive and stuff. +%d EXP.",
        "Through the ashes, you rise triumphant. +%d EXP.",
        "The task is complete. You have done well. +%d EXP.",
        "Impressive. Most impressive. +%d EXP.",
        "Most underestimate the power of focus and discipline, but not you. +%d EXP.",
        "The force is strong with you. +%d EXP.",
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

