package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.Custom_dark_blue
import com.example.to_docompose.ui.theme.Custom_white_background

/**
 * Composable Quick board and Quick board Item
 */

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
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Custom_white_background
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Bold title
            Text(
                text = task.title,
                style = androidx.compose.ui.text.TextStyle(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                color = Custom_dark_blue
            )
            // Limited description of the quest content for the quickboard, otherwise the third button get cut off
            Text(
                text = task.description.take(30) + if (task.description.length > 30) "..." else "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
                color = Custom_dark_blue
            )
        }
    }
}