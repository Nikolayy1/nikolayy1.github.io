@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.components.DisplayAlertDialog
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.Custom_dark_blue
import com.example.to_docompose.ui.theme.Custom_light_blue
import com.example.to_docompose.ui.theme.Custom_white
import com.example.to_docompose.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    onQuickBoardToggle: (ToDoTask) -> Unit
) {
    if (selectedTask == null) {
        // Safeguard: Show new task app bar only if ID is 0 (new tasks)
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)
    } else {
        // Existing task handling
        ExistingTaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = navigateToListScreen,
            onQuickBoardToggle = onQuickBoardToggle
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
            Text(
                color = Custom_white,
                text = stringResource(id = R.string.add_task)
            )
        },
        actions = {
            Row {
                IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.no_action),
                        tint = Custom_white
                    )
                }
                IconButton(onClick = { navigateToListScreen(Action.ADD) }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.add_task),
                        tint = Custom_white
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Custom_dark_blue
        )
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
        navigationIcon = {
            IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_list),
                    tint = Custom_white
                )
            }
        },
        title = {
            Text(text = selectedTask.title, color = Custom_white)
        },
        actions = {
            ExistingTaskAppBarActions(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen,
                onQuickBoardToggle = onQuickBoardToggle
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Custom_dark_blue
        )
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
            imageVector = if (selectedTask.isQuickBoard) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = stringResource(id = R.string.toggle_quickboard),
            tint = if (selectedTask.isQuickBoard) Custom_white else Custom_light_blue
        )
    }

    // Delete action
    IconButton(onClick = { openDialog = true }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete_task),
            tint = Custom_white
        )
    }

    // Update action
    IconButton(onClick = { navigateToListScreen(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Default.Save,
            contentDescription = stringResource(id = R.string.update_task),
            tint = Custom_white
        )
    }
}
