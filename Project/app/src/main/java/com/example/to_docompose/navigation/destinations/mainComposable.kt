package com.example.to_docompose.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.to_docompose.navigation.Screen
import com.example.to_docompose.ui.screens.main.MainScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel

fun NavGraphBuilder.mainComposable(
    navigateToListScreen: () -> Unit,
    sharedViewModel: SharedViewModel,
    navController: NavController
) {
    composable(route = Screen.Main.route) {
        MainScreen(
            navigateToListScreen = navigateToListScreen,
            sharedViewModel = sharedViewModel,
            navController = navController
        )
    }
}
