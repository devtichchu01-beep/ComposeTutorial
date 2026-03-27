package com.example.composetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.mainUI.language.LanguageScreen
import com.example.composetutorial.mainUI.language.LanguageViewModel
import com.example.composetutorial.mainUI.onboard.OnboardingScreenMVI
import com.example.composetutorial.mainUI.setting.SettingScreen
import com.example.composetutorial.mainUI.onboard.OnboardingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : OnboardingViewModel = viewModel()
    val languageViewModel : LanguageViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "onboarding"
    ) {
        composable("onboarding") {
            OnboardingScreenMVI(viewModel, navController)
        }

        composable("language") {
            LanguageScreen(languageViewModel, navController)
        }

        composable("setting") {
            SettingScreen(languageViewModel, navController)
        }
    }
}