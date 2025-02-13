package com.example.to_docompose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_docompose.navigation.destinations.listComposable
import com.example.to_docompose.navigation.destinations.mainComposable
import com.example.to_docompose.navigation.destinations.taskComposable
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action

@ExperimentalAnimationApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        mainComposable(
            navigateToListScreen = {
                navController.navigate(Screen.List.createRoute(Action.NO_ACTION))
            },
            sharedViewModel = sharedViewModel,
            navController = navController
        )
        listComposable(
            navigateToTaskScreen = { taskId ->
                navController.navigate(Screen.Task.createRoute(taskId))
            },
            navigateToMainScreen = {
                navController.navigate(Screen.Main.route)
            },
            sharedViewModel = sharedViewModel,
        )
        taskComposable(
            navigateToListScreen = { action ->
                navController.navigate(Screen.List.createRoute(action)) {
                    popUpTo(Screen.List.route) { inclusive = true }
                }
            },
            sharedViewModel = sharedViewModel
        )

    }
}
