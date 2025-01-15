package com.example.to_docompose.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.example.to_docompose.ui.viewmodels.SharedViewModel

@Composable
fun MainScreen(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    // Collect states from SharedViewModel
    val avatarLevel by sharedViewModel.currentLevel.collectAsState()
    val currentXP by sharedViewModel.currentXP.collectAsState()
    val xpForNextLevel by sharedViewModel.xpForNextLevel.collectAsState()
    val xpProgress by sharedViewModel.progressBar.collectAsState()
    val quickBoardTasks = sharedViewModel.quickBoardTasks.collectAsState().value

    // Pass collected states to MainContent
    MainContent(
        navigateToListScreen = navigateToListScreen,
        avatarLevel = avatarLevel,
        currentXP = currentXP,
        xpForNextLevel = xpForNextLevel,
        xpProgress = xpProgress,
        quickBoardTasks = quickBoardTasks,
        navigateToTaskScreen = { taskId -> navController.navigate("task_screen/$taskId")
        }
    )
}
