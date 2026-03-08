package com.example.smarttodomanager.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.smarttodomanager.data.SessionManager
import com.example.smarttodomanager.ui.screens.LoginScreen
import com.example.smarttodomanager.ui.screens.SignupScreen
import com.example.smarttodomanager.ui.screens.DashboardScreen
import com.example.smarttodomanager.ui.screens.AddTaskScreen
import com.example.smarttodomanager.ui.screens.EditTaskScreen
import com.example.smarttodomanager.viewmodel.AppViewModelProvider
import com.example.smarttodomanager.viewmodel.AuthViewModel
import com.example.smarttodomanager.viewmodel.TaskViewModel

@Composable
fun AppNavigation(sessionManager: SessionManager) {
    val navController = rememberNavController()

    val startDestination = if (sessionManager.isUserLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        
        composable(Screen.Login.route) {
            val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }
        
        composable(Screen.Signup.route) {
            val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
            SignupScreen(
                authViewModel = authViewModel,
                onSignupSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            val taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
            DashboardScreen(
                taskViewModel = taskViewModel,
                onLogout = {
                    sessionManager.clearSession()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToAddTask = {
                    navController.navigate(Screen.AddTask.route)
                },
                onNavigateToEditTask = { taskId ->
                    navController.navigate(Screen.EditTask.createRoute(taskId))
                }
            )
        }
        
        composable(Screen.AddTask.route) {
            val taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
            AddTaskScreen(
                taskViewModel = taskViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EditTask.route,
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId") ?: -1
            val taskViewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
            EditTaskScreen(
                taskId = taskId,
                taskViewModel = taskViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
