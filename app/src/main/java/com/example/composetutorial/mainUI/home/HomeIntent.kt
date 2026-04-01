package com.example.composetutorial.mainUI.home

import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.model.PDFFile

sealed class HomeIntent() {
    object AllTabClicked : HomeIntent()
    object StarredTabClicked : HomeIntent()

    object SetVerticalClicked : HomeIntent()
    object SetHorizontalClicked: HomeIntent()

    object SetVerticalStarClicked : HomeIntent()
    object SetHorizontalStarClicked: HomeIntent()

    data class ToggleStar(val pdfFile: PDFFile) : HomeIntent()

    data class SetShowBottom(val pdfFile: PDFFile) : HomeIntent()

    data class SetShowSecondBottom(val pdfFile: PDFFile) : HomeIntent()

    data class SetShowRenameDialog(val pdfFile: PDFFile) : HomeIntent()
}