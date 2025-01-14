package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask

@Composable
fun MainContent(
    navigateToListScreen: () -> Unit,
    avatarLevel: Int = 1,
    xpProgress: Float = 0.5f,
    quickBoardTasks: List<ToDoTask> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Level $avatarLevel", fontSize = 24.sp)
            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "XP Progress")
        LinearProgressIndicator(
            progress = xpProgress,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Quick Board", fontSize = 18.sp)
        if (quickBoardTasks.isEmpty()) {
            Text(text = "No tasks marked for Quick Board.")
        } else {
            QuickBoard(tasks = quickBoardTasks)
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = navigateToListScreen) { // Use the provided function for navigation
            Text(text = "Quest List")
        }
    }
}
