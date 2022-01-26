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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewPagerFragment @Inject constructor() : BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(
    FragmentViewPagerBinding::inflate
) {

    override val vm: ViewPagerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewpager with list of fragments
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

        // initialize tablayout with names
        TabLayoutMediator(layout.tabLayout, layout.viewPager) {tab, position ->
            when (position) {
                0 -> tab.text = "Inspiration"
                1 -> tab.text = "Notes"
                2 -> tab.text = "Set Goals"
            }
        }.attach()

//         listener if tab changes and it is possible to make specific action
        layout.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

}