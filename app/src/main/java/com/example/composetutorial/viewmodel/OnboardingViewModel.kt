package com.example.composetutorial.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.composetutorial.model.OnboardingState

class OnboardingViewModel : ViewModel() {
    var state by mutableStateOf(OnboardingState())
        private set

    fun send(intent: OnboardingIntent) {
        when(intent) {
            is OnboardingIntent.NextPage ->
            {
                state = state.copy(currentPage = state.currentPage + 1)
            }

            is OnboardingIntent.SetPage -> {
                state = state.copy(currentPage = intent.page)
            }
            else -> {

            }
        }
    }
}