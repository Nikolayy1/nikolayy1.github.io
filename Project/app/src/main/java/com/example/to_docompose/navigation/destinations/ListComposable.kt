package com.example.to_docompose.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.navigation.Screen
import com.example.to_docompose.ui.screens.list.ListScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action

@ExperimentalAnimationApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    navigateToMainScreen: () -> Unit
) {
    composable(
        route = Screen.List.route,
        arguments = listOf(navArgument("action") { defaultValue = Action.NO_ACTION.name })
    ) { navBackStackEntry ->
        val actionString = navBackStackEntry.arguments?.getString("action") ?: Action.NO_ACTION.name
        val action = Action.valueOf(actionString)
        var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }

        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
                sharedViewModel.updateAction(newAction = action)
            }
        }

        val databaseAction = sharedViewModel.action

        ListScreen(
            action = databaseAction,
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
            navigateToMainScreen = navigateToMainScreen
        )
    }
}
