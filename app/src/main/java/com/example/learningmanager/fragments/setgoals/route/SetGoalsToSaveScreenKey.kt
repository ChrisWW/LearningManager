package com.example.learningmanager.fragments.setgoals.route

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey
import com.example.learningmanager.fragments.ViewPagerFragmentDirections
import com.example.learningmanager.fragments.auth.ui.SingleModeAuthFragmentDirections

class SetGoalsToSaveScreenKey : ScreenKey() {
    override fun createDirections(): NavDirections {
        return ViewPagerFragmentDirections.actionViewPagerFragmentToSaveGoalFragment()
    }
}