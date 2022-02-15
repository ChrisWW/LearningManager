package com.example.learningmanager.base.navigation

import androidx.navigation.NavDirections

abstract class ScreenKey {
    abstract fun createDirections() : NavDirections
}