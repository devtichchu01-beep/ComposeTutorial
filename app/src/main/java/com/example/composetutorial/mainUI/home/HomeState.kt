package com.example.composetutorial.mainUI.home

import androidx.compose.ui.res.stringResource
import com.example.composetutorial.R
import com.example.composetutorial.model.PDFFile
import com.example.composetutorial.model.Sort

data class HomeState(
    val selectedTab : String = "All",
    val setVertical : Boolean = false,
    val setVerticalStar: Boolean = false,
    val showBottom: Boolean = false,
    val showSecondBottom: Boolean = false,
    val selectedPDF: PDFFile? = null,
    val showRenameDialog : Boolean = false,
    val showDeleteDialog : Boolean = false,
    val showSortBottom: Boolean = false,
    val selectedSort : Sort? = Sort(
        1,
        R.drawable.new_to_old,
        "Last Modified",
        "(new to old)"
    )
)