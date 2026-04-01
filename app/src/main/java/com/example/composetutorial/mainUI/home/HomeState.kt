package com.example.composetutorial.mainUI.home

import com.example.composetutorial.model.PDFFile

data class HomeState(
    val selectedTab : String = "All",
    val setVertical : Boolean = false,
    val setVerticalStar: Boolean = false,
    val showBottom: Boolean = false,
    val showSecondBottom: Boolean = false,
    val selectedPDF: PDFFile? = null,
    val showRenameDialog : Boolean = false
)