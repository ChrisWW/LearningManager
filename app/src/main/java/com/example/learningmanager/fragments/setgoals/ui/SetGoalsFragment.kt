package com.example.learningmanager.fragments.setgoals.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ext.collectWith
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSetGoalsBinding
import com.example.learningmanager.fragments.setgoals.adapters.SetGoalsAdapter
import javax.inject.Inject

class SetGoalsFragment @Inject constructor() : BaseFragment<FragmentSetGoalsBinding, SetGoalsViewModel>(
    FragmentSetGoalsBinding::inflate
) {
    override val vm: SetGoalsViewModel by viewModels()
    private lateinit var setGoalsAdapter: SetGoalsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setGoalsAdapter = SetGoalsAdapter(vm::deleteItem, vm::onItemClicked)

//        backgroundAnimation()
        onFabClicked()
        initItemsRecyclerView()

        collectGoalsItems()
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
        vm.setGoalsData.collectWith(viewLifecycleOwner) {
            if (it.isEmpty()) {
            } else {
                setGoalsAdapter.items = it
                setGoalsAdapter.notifyDataSetChanged()
            }
        }
    }

    fun onFabClicked() {
        layout.idFabGoals.setOnClickListener {
            vm.onNavigateToSave()
        }
    }
}
