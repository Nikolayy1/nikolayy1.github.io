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

@Composable
fun MainScreen(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = navigateToListScreen,
            modifier = Modifier.fillMaxSize(0.5f)
        ) {
            Text(text = "Go to Quest List")
        }
    }
}
