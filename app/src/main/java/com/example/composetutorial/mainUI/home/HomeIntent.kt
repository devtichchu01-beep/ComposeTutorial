package com.example.composetutorial.mainUI.home

import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.model.PDFFile

sealed class HomeIntent() {
    object AllTabClicked : HomeIntent()
    object StarredTabClicked : HomeIntent()

    object SetVerticalClicked : HomeIntent()
    object SetHorizontalClicked: HomeIntent()

    data class ToggleStar(val pdfFile: PDFFile) : HomeIntent()
}