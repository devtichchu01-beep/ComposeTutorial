package com.example.composetutorial.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomItem("home", Icons.Default.Home, "Home")
    object Recent : BottomItem("recent", Icons.Default.Share, "Recent")
    object File : BottomItem("file", Icons.Default.Info, "File")
    object Setting: BottomItem("setting", Icons.Default.Settings, "Settings")

}
