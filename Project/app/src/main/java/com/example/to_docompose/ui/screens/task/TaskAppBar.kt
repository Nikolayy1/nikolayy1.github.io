@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.components.DisplayAlertDialog
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    onQuickBoardToggle: (ToDoTask) -> Unit // Added parameter for QuickBoard toggle
) {
    if (selectedTask == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        ExistingTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen,
            onQuickBoardToggle = onQuickBoardToggle // Pass toggle handler
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.add_task))
        },
        actions = {
            Row {
                IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.no_action)
                    )
                }
                IconButton(onClick = { navigateToListScreen(Action.ADD) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.add_task)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit,
    onQuickBoardToggle: (ToDoTask) -> Unit
) {
    TopAppBar(
        title = {
            Text(text = selectedTask.title)
        },
        actions = {
            ExistingTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen,
                onQuickBoardToggle = onQuickBoardToggle
            )
        }
    )
}

@Composable
fun ExistingTaskAppBarActions(
    selectedTask: ToDoTask,
    navigateToListScreen: (Action) -> Unit,
    onQuickBoardToggle: (ToDoTask) -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    // Delete confirmation dialog
    DisplayAlertDialog(
        title = stringResource(
            id = R.string.delete_task,
            selectedTask.title
        ),
        message = stringResource(
            id = R.string.delete_task_confirmation,
            selectedTask.title
        ),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE) }
    )

    // QuickBoard toggle action
    IconButton(onClick = { onQuickBoardToggle(selectedTask) }) {
        Icon(
            imageVector = Icons.Default.Bookmark, // QuickBoard toggle icon
            contentDescription = stringResource(id = R.string.toggle_quickboard),
            tint = if (selectedTask.isQuickBoard) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    }

    // Delete action
    IconButton(onClick = { openDialog = true }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_task)
        )
    }

    // Update action
    IconButton(onClick = { navigateToListScreen(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(id = R.string.update_task)
        )
    }
}
