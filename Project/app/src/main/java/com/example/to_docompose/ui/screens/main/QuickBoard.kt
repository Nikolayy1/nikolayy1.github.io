package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.to_docompose.data.models.ToDoTask

@Composable
fun QuickBoard(tasks: List<ToDoTask>) {
    LazyColumn {
        items(tasks) { task ->
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = task.title)
                Text(text = task.description)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
