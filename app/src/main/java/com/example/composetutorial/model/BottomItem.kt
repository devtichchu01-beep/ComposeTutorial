package com.example.composetutorial.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.composetutorial.R

sealed class BottomItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    val title: String
) {
    object Home : BottomItem("home", R.drawable.ic_home, "Home")
    object Recent : BottomItem("recent", R.drawable.ic_clock, "Recent")
    object File : BottomItem("file", R.drawable.ic_file, "File")
    object Setting: BottomItem("setting", R.drawable.ic_set, "Settings")

}
