package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.to_docompose.data.models.ToDoTask

@Composable
fun QuickBoard(
    tasks: List<ToDoTask>,
    onTaskClick: (Int) -> Unit // Define navigation parameter
) {
    Column {
        if (tasks.isEmpty()) {
            Text(
                text = "No tasks marked for QuickBoard.",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            tasks.forEach { task ->
                QuickBoardTaskItem(
                    task = task,
                    onClick = { onTaskClick(task.id) } // Pass task ID for navigation
                )
            }
        }
    }
}

@Composable
fun QuickBoardTaskItem(
    task: ToDoTask,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }, // Navigate when clicked
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title)
            Text(text = task.description, modifier = Modifier.padding(top = 4.dp))
        }
    }
}
