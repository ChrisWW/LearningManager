package com.example.learningmanager.fragments.setgoals.ui

import android.app.Dialog
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ext.collectWith
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSetGoalsBinding
import com.example.learningmanager.fragments.setgoals.adapters.SetGoalsAdapter
import com.example.learningmanager.fragments.setgoals.data.CustomSetGoalsDialogData
import com.example.learningmanager.fragments.setgoals.helpers.CustomSetGoalsDialog
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetGoalsFragment @Inject constructor() :
    BaseFragment<FragmentSetGoalsBinding, SetGoalsViewModel>(
        FragmentSetGoalsBinding::inflate
    ) {
    override val vm: SetGoalsViewModel by viewModels()
    private val setGoalsAdapter: SetGoalsAdapter by initItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setGoalsAdapter = SetGoalsAdapter(vm::deleteItem, vm::onItemClicked)
//        backgroundAnimation()

        initItemsRecyclerView()
        collectGoalsItems()
        onFabClicked()
        onAcceptDeclineTodayWork()
    }

    override fun onDestroyView() {
        layout.idRvItemsMain.adapter = null
        super.onDestroyView()
    }

    private fun initItemsRecyclerView() {
        layout.idRvItemsMain.adapter = setGoalsAdapter
    }

    private fun backgroundAnimation() {
        val animationDrawable: AnimationDrawable = layout.rlLayout.background as AnimationDrawable

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3600)
            start()
        }
    }

    private fun collectGoalsItems() {
        vm.getActualState()
        vm.setGoalsData.collectWith(viewLifecycleOwner) {
            if (it.isEmpty()) {
            } else {
                setGoalsAdapter.items = it
                setGoalsAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onFabClicked() {
        layout.idFabGoals.setOnClickListener {
            vm.onNavigateToSave()
        }
    }

    private fun onAcceptDeclineTodayWork() {
        vm.triggerAcceptDeclineButton.collectWith(viewLifecycleOwner) { it ->
            if(it.isSelected()) {
                CustomSetGoalsDialog(requireContext(), id).show(
                    "Save your task: ${it.title}",
                    "Clicking NO - means you haven't done your work and one additional day will be added to complete your goal."
                ) {
                    Toast.makeText(
                        context,
                        it.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                vm.triggerAcceptDeclineButton.value = CustomSetGoalsDialogData.empty
            }
        }
    }

    private fun initItemsAdapter() = lazy {
        SetGoalsAdapter(vm::deleteItem, vm::onItemClicked, vm::onItemButtonQuestionClicked)
    }
}
