package com.example.learningmanager.fragments.setgoals.route

import androidx.navigation.NavDirections
import com.example.learningmanager.base.navigation.ScreenKey
import com.example.learningmanager.fragments.ViewPagerFragmentDirections
import com.example.learningmanager.fragments.setgoals.ui.SaveGoalFragmentDirections

class SaveGoalsToSetScreenKey : ScreenKey() {
    override fun createDirections(): NavDirections {
        return SaveGoalFragmentDirections.actionSaveGoalFragmentToViewPagerFragment()
    }
}