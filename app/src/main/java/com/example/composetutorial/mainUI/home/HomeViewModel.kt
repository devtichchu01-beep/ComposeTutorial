package com.example.composetutorial.mainUI.home

import android.content.Context
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.model.PDFFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

class HomeViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(HomeState())
    val selectedTab : StateFlow<HomeState> = _selectedTab
    private val _pdfLists = MutableStateFlow<List<PDFFile>>(emptyList())
    val pdfLists : StateFlow<List<PDFFile>> = _pdfLists

    fun loadPDFFiles(context : Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = getPDFFiles(context)
            withContext(Dispatchers.Main) {
                _pdfLists.value = files
            }
        }
    }
    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.AllTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "All")
            }
            is HomeIntent.StarredTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "Starred")
            }
            is HomeIntent.SetVerticalClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVertical = true)
            }
            is HomeIntent.SetHorizontalClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVertical = false)
            }
            is HomeIntent.SetVerticalStarClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVerticalStar = true)
            }
            is HomeIntent.SetHorizontalStarClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVerticalStar = false)
            }
            is HomeIntent.ToggleStar -> {
                _pdfLists.value = _pdfLists.value.map {
                    if (it == intent.pdfFile) {
                        it.copy(isStarred = !it.isStarred)
                    } else it
                }
            }
            is HomeIntent.SetShowBottom -> {
                _selectedTab.value = _selectedTab.value.copy(
                    showBottom = !_selectedTab.value.showBottom,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowSecondBottom -> {
                _selectedTab.value = _selectedTab.value.copy(
                    showSecondBottom = !_selectedTab.value.showSecondBottom,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowRenameDialog -> {
                _selectedTab.value = selectedTab.value.copy(
                    showBottom = false,
                    showSecondBottom = false,
                    showRenameDialog = !_selectedTab.value.showRenameDialog,
                    selectedPDF = intent.pdfFile
                )
            }
        }
    }
}