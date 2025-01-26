package com.example.to_docompose.ui.screens.main

import android.util.Log
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
    val stats by sharedViewModel.stats.collectAsState()
    val selectedAvatar by sharedViewModel.selectedAvatar.collectAsState()


    /// onStatUpgrade callback
    val onStatUpgrade: (String) -> Unit = { stat ->
        sharedViewModel.upgradeStat(stat)
    }

    Log.d("MainScreen", "Stats updated: $stats") // Add this log to verify updates, delete later.

    // Pass collected states to MainContent
    MainContent(
        navigateToListScreen = navigateToListScreen,
        avatarLevel = avatarLevel,
        currentXP = currentXP,
        xpForNextLevel = xpForNextLevel,
        xpProgress = xpProgress,
        quickBoardTasks = quickBoardTasks,
        stats = stats,
        onStatUpgrade = { statName -> sharedViewModel.upgradeStat(statName) },
        selectedAvatar = selectedAvatar,
        onAvatarChange = { avatar -> sharedViewModel.saveSelectedAvatar(avatar) },
        navigateToTaskScreen = { taskId -> navController.navigate("task_screen/$taskId")
        }
    )
}
