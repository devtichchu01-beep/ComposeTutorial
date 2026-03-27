package com.example.composetutorial.mainUI.language

import android.health.connect.datatypes.SleepSessionRecord
import android.media.audiofx.DynamicsProcessing
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.composetutorial.mainUI.language.LanguageState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel : ViewModel() {
    private val _state = MutableStateFlow(LanguageState())
    val state : StateFlow<LanguageState> = _state

    fun handleIntent(intent: LanguageIntent) {
        when(intent) {
            is LanguageIntent.SelectedLanguage -> {
                _state.value = _state.value.copy(selectedLanguage = intent.language)
            }
        }
    }
}