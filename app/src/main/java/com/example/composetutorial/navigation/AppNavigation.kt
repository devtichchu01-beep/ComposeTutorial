package com.example.composetutorial.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.mainUI.bottom.BottomScreen
import com.example.composetutorial.mainUI.language.LanguageScreen
import com.example.composetutorial.mainUI.language.LanguageViewModel
import com.example.composetutorial.mainUI.onboard.OnBoardingSplash
import com.example.composetutorial.mainUI.onboard.OnboardingScreen
import com.example.composetutorial.mainUI.setting.SettingScreen
import com.example.composetutorial.mainUI.onboard.OnboardingViewModel
import com.example.composetutorial.model.BottomItem

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel : OnboardingViewModel = viewModel()
    val languageViewModel : LanguageViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "onboardingSplash"
    ) {
        composable("bottom") {
            BottomScreen()
        }
        composable("onboarding") {
            OnboardingScreen(viewModel, navController)
        }
        composable("onboardingSplash") {
            OnBoardingSplash(navController)
        }
    }
}