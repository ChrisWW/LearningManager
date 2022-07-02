package com.example.learningmanager.fragments

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey
import com.example.learningmanager.fragments.ViewPagerFragmentDirections

class ViewPagerToMyInspirationScreenKey : ScreenKey() {
    override fun createDirections(): NavDirections {
        return ViewPagerFragmentDirections.actionViewPagerFragmentToMyInspirationFragment()
    }
}