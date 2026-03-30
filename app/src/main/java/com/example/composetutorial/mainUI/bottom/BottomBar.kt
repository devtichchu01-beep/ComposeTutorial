package com.example.composetutorial.mainUI.bottom

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.composetutorial.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
        containerColor = Color.White
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
                    selectedIconColor = colorResource(R.color.cyan),
                    selectedTextColor = colorResource(R.color.cyan),
                    unselectedIconColor = colorResource(R.color.gray_thin),
                    unselectedTextColor =  colorResource(R.color.gray_thin),
                )
            )
        }
    }
}