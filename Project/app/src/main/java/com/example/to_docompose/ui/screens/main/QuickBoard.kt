package com.example.to_docompose.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.to_docompose.data.models.ToDoTask

@Composable
fun QuickBoard(tasks: List<ToDoTask>) {
    LazyColumn {
        items(tasks) { task ->
            Column {
                Text(text = task.title)
                Text(text = task.description)
            }
        }
    }
}
