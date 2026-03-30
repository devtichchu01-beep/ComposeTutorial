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
import com.example.composetutorial.mainUI.language.LanguageScreen
import com.example.composetutorial.mainUI.language.LanguageViewModel
import com.example.composetutorial.mainUI.recent.RecentScreen
import com.example.composetutorial.mainUI.setting.SettingScreen
import com.example.composetutorial.model.BottomItem
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
            startDestination = BottomItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomItem.Home.route) {HomeScreen(navController, homeViewModel)}
            composable(BottomItem.Recent.route) {RecentScreen(navController, homeViewModel)}
            composable(BottomItem.File.route) {FileScreen()}
            composable("language") {LanguageScreen(languageViewModel, navController)}
            composable(BottomItem.Setting.route) {SettingScreen(languageViewModel, navController)}
        }
    }
}