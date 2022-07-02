package com.example.learningmanager.fragments

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentViewPagerBinding
import com.example.learningmanager.databinding.ItemHeaderBinding
import com.example.learningmanager.fragments.inspirationquotes.ui.InspirationQuotesFragment
import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.example.learningmanager.fragments.notesmanager.ui.NotesManagerFragment
import com.example.learningmanager.fragments.setgoals.ui.SetGoalsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ViewPagerFragment @Inject constructor() :
    BaseFragment<FragmentViewPagerBinding, ViewPagerViewModel>(
        FragmentViewPagerBinding::inflate
    ) {
    override val vm: ViewPagerViewModel by viewModels()
    private val args: ViewPagerFragmentArgs by navArgs()
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var act: AppCompatActivity
//    val firebaseManager = firebaseManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initTabs()
        // TODO drawerUsage doesn't work gives bug with button on inspiration
        drawerUsage()
//
//       layout.drawerLayout.width = WindowManager.LayoutParams.MATCH_PARENT
//       layout.drawerLayout.height = WindowManager.LayoutParams.MATCH_PARENT
//


    }

    override fun onDestroyView() {
        layout.viewPager.adapter = null

        super.onDestroyView()
    }

    // initialize viewpager with list of fragments
    private fun initViewPager() {
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
//        layout.viewPager.isUserInputEnabled = false
    }

    private fun drawerUsage() {
        act = activity as AppCompatActivity
        val drawer = layout.drawerLayout
        val navigationView = layout.navView
        act.setSupportActionBar(layout.appBarLayout)
        toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawer,
            layout.appBarLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        act!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()
        val headerView = navigationView.getHeaderView(0)
        val drawerHeaderBinding: ItemHeaderBinding = ItemHeaderBinding.bind(headerView)
////        navigationView.addHeaderView(drawerHeaderBinding.root)
        val imageUri = Uri.parse(args.photoUrl)
        drawerHeaderBinding.txEmail.text = args.userEmail
        drawerHeaderBinding.txDisplayName.text = args.displayName
        if (imageUri != null) {
            drawerHeaderBinding.ivAvatar.setImageURI(null)
            drawerHeaderBinding.ivAvatar.setImageURI(imageUri)
        }
////
        drawer.setViewScale(Gravity.START, 0.9f);
        //set height scale for main view (0f to 1f)
        drawer.setViewElevation(Gravity.START, 20f);
        //set main view elevation when drawer open (dimension)
        drawer.setViewScrimColor(Gravity.START, Color.TRANSPARENT);
        //set drawer overlay coloe (color)
        drawer.setDrawerElevation(Gravity.START, 20f);
        //set drawer elevation (dimension
        drawer.setRadius(Gravity.START, 25f);
        //set end container's corner radius (dimension)

        navigationView.setNavigationItemSelectedListener {
            val auth = FirebaseAuth.getInstance()
            when (it.itemId) {
                R.id.home -> Toast.makeText(requireContext(), "Clicked Home", Toast.LENGTH_SHORT)
                    .show()
                R.id.profile -> Toast.makeText(
                    requireContext(),
                    "Clicked Profile",
                    Toast.LENGTH_SHORT
                )
                    .show()
                R.id.inspirations -> vm.navigateTo(ViewPagerToMyInspirationScreenKey())
                R.id.settings -> Toast.makeText(
                    requireContext(),
                    "Clicked settings",
                    Toast.LENGTH_SHORT
                )
                    .show()
                R.id.logout -> auth.signOut()
                    .let { vm.navigateTo(ViewPagerToAuthScreenKey()) }
            }
            true
        }
    }

    // initialize tablayout with names
    private fun initTabs() {

        TabLayoutMediator(layout.tabLayout, layout.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Inspiration"
                1 -> tab.text = "Notes"
                2 -> tab.text = "Set Goals"
            }
        }.attach()

        //         listener if tab changes and it is possible to make specific action
        layout.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            layout.drawerLayout.openDrawer(Gravity.LEFT)
            return true
        }

//        item.setOnMenuItemClickListener {
//            layout.drawerLayout.openDrawer(Gravity.LEFT)
//            true
//        }
//        if(item.itemId == android.R.id.home){ // use android.R.id
//            layout.drawerLayout.openDrawer(Gravity.LEFT);
//        }
        return super.onOptionsItemSelected(item)
    }
}

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        if(toggle.onOptionsItemSelected(item)) {
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }




