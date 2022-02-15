package com.example.learningmanager.fragments.setgoals.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSaveGoalBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SaveGoalFragment @Inject constructor() :
    BaseFragment<FragmentSaveGoalBinding, SaveGoalsViewModel>(
        FragmentSaveGoalBinding::inflate
    ) {

    override val vm: SaveGoalsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickBack()

    }

    private fun onClickBack() {
        layout.backBtn.setOnClickListener {
            vm.navigateBack()
        }
    }
}