package com.example.composetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.view.LanguageScreen
import com.example.composetutorial.view.OnboardingScreenMVI
import com.example.composetutorial.view.SettingScreen
import com.example.composetutorial.viewmodel.OnboardingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : OnboardingViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreenMVI(viewModel, navController)
        }

        composable("language") {
            LanguageScreen(navController)
        }

        composable("setting") {
            SettingScreen(navController)
        }
    }
}