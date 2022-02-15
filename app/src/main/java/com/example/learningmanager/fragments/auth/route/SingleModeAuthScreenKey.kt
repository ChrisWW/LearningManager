package com.example.learningmanager.fragments.auth.route

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey
import com.example.learningmanager.fragments.auth.ui.SingleModeAuthFragmentDirections

class SingleModeAuthScreenKey : ScreenKey() {
    override fun createDirections(): NavDirections {
        return SingleModeAuthFragmentDirections.actionSingleModeAuthFragment2ToViewPagerFragment()
    }
}