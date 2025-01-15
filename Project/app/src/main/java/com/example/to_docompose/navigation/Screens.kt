@file:Suppress("ConvertObjectToDataObject")

package com.example.to_docompose.navigation

import com.example.to_docompose.util.Action

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object List : Screen("list/{action}") {
        fun createRoute(action: Action) = "list/${action.name}"
    }
    object Task : Screen("task/{taskId}") {
        fun createRoute(taskId: Int) = "task/$taskId"
    }
}
