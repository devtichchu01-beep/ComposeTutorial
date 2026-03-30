package com.example.composetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.mainUI.bottom.BottomScreen
import com.example.composetutorial.mainUI.onboard.OnBoardingSplash
import com.example.composetutorial.mainUI.onboard.OnboardingScreen
import com.example.composetutorial.mainUI.onboard.OnboardingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : OnboardingViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = onboardingSplashNav
    ) {
        composable(bottomNav) {
            BottomScreen()
        }
        composable(onboardingNav) {
            OnboardingScreen(viewModel, navController)
        }
        composable(onboardingSplashNav) {
            OnBoardingSplash(navController)
        }
    }
}