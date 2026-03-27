package com.example.composetutorial.mainUI.bottom

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.composetutorial.model.BottomItem

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomItem.Home,
        BottomItem.Recent,
        BottomItem.File,
        BottomItem.Setting
    )

    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route

    NavigationBar(
        containerColor = Color.White,
    ) {
        items.forEach {
            items -> NavigationBarItem(
                selected = currentRoute == items.route,
                onClick = {
                    navController.navigate(items.route) {
                        popUpTo(BottomItem.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(items.icon, contentDescription = items.title)
                },
                label = {Text(items.title)},
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF0485F8),
                    selectedTextColor = Color(0xFF0485F8),
                    unselectedIconColor = Color(0xFFD3D3D3),
                    unselectedTextColor =  Color(0xFFD3D3D3)
                )
            )
        }
    }
}