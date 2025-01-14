package com.example.to_docompose.ui.screens.main

import androidx.compose.runtime.Composable
import com.example.to_docompose.ui.viewmodels.SharedViewModel

@Composable
fun MainScreen(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    MainContent(
        navigateToListScreen = navigateToListScreen,
        avatarLevel = sharedViewModel.currentLevel,
        xpProgress = sharedViewModel.progressBar,
        quickBoardTasks = emptyList()
    )
}
