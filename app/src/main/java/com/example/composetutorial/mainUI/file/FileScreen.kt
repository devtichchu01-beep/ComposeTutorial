package com.example.composetutorial.mainUI.file

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun FileScreen() {
    Box(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))))) {
        Text(text = "File", fontSize = 40.sp, modifier = Modifier.align(alignment = Alignment.Center))
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    FileScreen()
}