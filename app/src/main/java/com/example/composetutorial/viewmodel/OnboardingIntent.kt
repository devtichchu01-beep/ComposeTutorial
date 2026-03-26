package com.example.composetutorial.viewmodel

sealed class OnboardingIntent {
    object NextPage : OnboardingIntent()
    object Complete : OnboardingIntent()
    data class SetPage(val page: Int) : OnboardingIntent()
}