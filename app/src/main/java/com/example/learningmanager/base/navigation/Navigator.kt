package com.example.learningmanager.base.navigation

interface Navigator {
    fun navigateTo(key: ScreenKey)
    fun navigateBack()
}