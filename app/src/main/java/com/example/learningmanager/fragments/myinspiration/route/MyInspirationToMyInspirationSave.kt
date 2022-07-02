package com.example.learningmanager.fragments.myinspiration.route

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey
import com.example.learningmanager.fragments.myinspiration.ui.MyInspirationFragmentDirections

class MyInspirationToMyInspirationSave : ScreenKey() {
    override fun createDirections(): NavDirections {
        return MyInspirationFragmentDirections.actionMyInspirationFragmentToMyInspirationSaveFragment()
    }
}