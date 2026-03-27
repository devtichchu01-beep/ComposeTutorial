package com.example.composetutorial.mainUI.onboard

sealed class OnboardingIntent {
    object NextPage : OnboardingIntent()
    object Complete : OnboardingIntent()
    data class SetPage(val page: Int) : OnboardingIntent()
}