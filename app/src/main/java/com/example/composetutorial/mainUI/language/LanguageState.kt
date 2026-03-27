package com.example.composetutorial.mainUI.language

import com.example.composetutorial.model.Language

data class LanguageState(
    val selectedLanguage: Language? = null,
) {
    val isSelected : Boolean
        get() = selectedLanguage != null
}