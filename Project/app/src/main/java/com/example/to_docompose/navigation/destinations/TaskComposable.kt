package com.example.to_docompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.Screen
import com.example.to_docompose.ui.screens.task.TaskScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action

/**
 * Composable route for single quest view (TaskScreen is entry point)
 */

@ExperimentalAnimationApi
fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = Screen.Task.route,
        arguments = listOf(navArgument("taskId") { defaultValue = -1 })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments?.getInt("taskId") ?: -1

        // Fetch the selected task when the screen is opened
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId = taskId)
        }

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        LaunchedEffect(key1 = selectedTask) {
            if (selectedTask != null || taskId == -1) {
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)
            }
        }

        TaskScreen(
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel,
            navigateToListScreen = navigateToListScreen
        )
    }
}
