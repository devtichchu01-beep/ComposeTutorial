package com.example.composetutorial.mainUI.home

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.model.PDFFile
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(HomeState())
    val selectedTab : StateFlow<HomeState> = _selectedTab

    private val _pdfLists = MutableStateFlow(initialPDFList())
    val pdfLists : StateFlow<List<PDFFile>> = _pdfLists

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
            is HomeIntent.ToggleStar -> {
                _pdfLists.value = _pdfLists.value.map {
                    if (it == intent.pdfFile) {
                        it.copy(isStarred = !it.isStarred)
                    } else it
                }
            }
        }
    }
}