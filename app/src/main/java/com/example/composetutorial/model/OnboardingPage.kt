package com.example.composetutorial.model

data class OnboardingPage(
    val image :Int,
    val title : String ="",
    val desc : String="",
    val isIntro : Boolean = false
)
