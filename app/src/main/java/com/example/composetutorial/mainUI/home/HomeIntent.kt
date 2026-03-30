package com.example.composetutorial.mainUI.home

import com.example.composetutorial.model.BottomItem

sealed class HomeIntent() {
    object AllTabClicked : HomeIntent()
    object StarredTabClicked : HomeIntent()
}