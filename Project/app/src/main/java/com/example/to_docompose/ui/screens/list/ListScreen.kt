package com.example.to_docompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.Custom_white_background
import com.example.to_docompose.ui.theme.Custom_orange
import com.example.to_docompose.ui.theme.Custom_white
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.SearchAppBarState

/**
 * Composable List Screen (Top Bar + Content)
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun ListScreen(
    action: Action,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action = action)
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState = sharedViewModel.searchAppBarState
    val searchTextState: String = sharedViewModel.searchTextState


    Scaffold(
        modifier = Modifier.background(Custom_white_background),
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState,
                navigateToMainScreen = navigateToMainScreen
            )
        },
        content = { padding ->
            ListContent(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
                allTasks = allTasks,
                searchedTasks = searchedTasks,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                searchAppBarState = searchAppBarState,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        containerColor = Custom_orange,
        contentColor = Custom_white
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(
                id = R.string.add_button
            )
        )
    }
}


