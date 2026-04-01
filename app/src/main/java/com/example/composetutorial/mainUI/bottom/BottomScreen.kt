package com.example.composetutorial.mainUI.bottom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.mainUI.file.FileScreen
import com.example.composetutorial.mainUI.home.HomeScreen
import com.example.composetutorial.mainUI.home.HomeViewModel
import com.example.composetutorial.mainUI.home.StarredScreen
import com.example.composetutorial.mainUI.language.LanguageScreen
import com.example.composetutorial.mainUI.language.LanguageViewModel
import com.example.composetutorial.mainUI.recent.RecentScreen
import com.example.composetutorial.mainUI.setting.SettingScreen
import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.navigation.bottomFileNav
import com.example.composetutorial.navigation.bottomHomeNav
import com.example.composetutorial.navigation.bottomRecentNav
import com.example.composetutorial.navigation.bottomSettingNav
import com.example.composetutorial.navigation.languageNav
import com.example.composetutorial.navigation.starredNav
import kotlinx.coroutines.flow.combine

@Composable
fun BottomScreen() {
    val navController = rememberNavController()
    val languageViewModel: LanguageViewModel = viewModel()
    val homeViewModel : HomeViewModel = viewModel()
    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = bottomHomeNav,
            modifier = Modifier.padding(padding)
        ) {
            composable(bottomHomeNav) {HomeScreen(navController, homeViewModel)}
            composable(bottomRecentNav) {RecentScreen(navController, homeViewModel)}
            composable(bottomFileNav) {FileScreen()}
            composable(languageNav) {LanguageScreen(languageViewModel, navController)}
            composable(bottomSettingNav) {SettingScreen(languageViewModel, navController)}
            composable(starredNav) { StarredScreen(navController, homeViewModel) }
        }
    }
}