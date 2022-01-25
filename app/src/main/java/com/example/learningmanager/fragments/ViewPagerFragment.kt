package com.example.learningmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentViewPagerBinding
import com.example.learningmanager.fragments.inspirationquotes.ui.InspirationQuotesFragment
import com.example.learningmanager.fragments.notesmanager.ui.NotesManagerFragment
import com.example.learningmanager.fragments.setgoals.ui.SetGoalsFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewPagerFragment @Inject constructor() : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(
    FragmentViewPagerBinding::inflate
) {

    override val vm: ViewPagerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            InspirationQuotesFragment(),
            NotesManagerFragment(),
            SetGoalsFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        layout.viewPager.adapter = adapter
    }

}