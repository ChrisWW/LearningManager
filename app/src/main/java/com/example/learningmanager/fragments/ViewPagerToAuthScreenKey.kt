package com.example.learningmanager.fragments

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey

class ViewPagerToAuthScreenKey : ScreenKey() {
    override fun createDirections(): NavDirections {
        return ViewPagerFragmentDirections.actionViewPagerFragmentToSingleModeAuthFragment2()
    }
}