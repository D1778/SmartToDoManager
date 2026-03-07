package com.example.smarttodomanager.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Dashboard : Screen("dashboard")
    object AddTask : Screen("add_task")
    object Categories : Screen("categories")
    object CompletedTasks : Screen("completed_tasks")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
