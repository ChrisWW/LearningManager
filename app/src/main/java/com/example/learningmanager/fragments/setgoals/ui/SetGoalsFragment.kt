package com.example.learningmanager.fragments.setgoals.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSetGoalsBinding
import javax.inject.Inject

class SetGoalsFragment @Inject constructor() : BaseFragment<FragmentSetGoalsBinding, SetGoalsViewModel>(
    FragmentSetGoalsBinding::inflate
) {
    override val vm: SetGoalsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundAnimation()
        onFabClicked()
    }


    private fun backgroundAnimation() {
        val animationDrawable: AnimationDrawable = layout.rlLayout.background as AnimationDrawable

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3600)
            start()
        }
    }

    fun onFabClicked() {
        layout.idFabGoals.setOnClickListener {
            vm.onNavigateToSave()
        }
    }
}
