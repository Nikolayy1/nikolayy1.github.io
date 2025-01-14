package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.to_docompose.ui.viewmodels.SharedViewModel

//MainScreen provides state and actions from sharedViewModel into MainContent.
@Composable
fun MainScreen(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    MainContent(
        navigateToListScreen = navigateToListScreen,
    )
}
