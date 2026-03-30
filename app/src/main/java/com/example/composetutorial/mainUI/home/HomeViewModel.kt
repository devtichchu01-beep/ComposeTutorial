package com.example.composetutorial.mainUI.home

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(HomeState())
    val selectedTab : StateFlow<HomeState> = _selectedTab

    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.AllTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "All")
            }
            is HomeIntent.StarredTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "Starred")
            }
        }
    }
}