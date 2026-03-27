package com.example.composetutorial.mainUI.language

import com.example.composetutorial.model.Language

sealed class LanguageIntent {
    data class SelectedLanguage(val language : Language) : LanguageIntent()
}