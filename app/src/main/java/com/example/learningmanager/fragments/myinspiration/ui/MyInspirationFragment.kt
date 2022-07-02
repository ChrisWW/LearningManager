package com.example.learningmanager.fragments.myinspiration.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentMyInspirationBinding
import com.example.learningmanager.databinding.ItemHeaderBinding
import com.example.learningmanager.fragments.ViewPagerToMyInspirationScreenKey
import com.example.learningmanager.fragments.myinspiration.route.MyInspirationToMyInspirationSave
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyInspirationFragment @Inject constructor() : BaseFragment<FragmentMyInspirationBinding, MyInspirationViewModel>(FragmentMyInspirationBinding::inflate) {
    override val vm: MyInspirationViewModel by viewModels()
//    private val args: MyInspirationFragmentArgs by navArgs()
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var act: AppCompatActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        drawerUsage()
    }










    private fun initUI() {
        navigateToInspirationSave()

    }

    private fun navigateToInspirationSave() {
        layout.addInspirationFab.setOnClickListener {
            vm.navigateTo(MyInspirationToMyInspirationSave())
        }
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
//        val imageUri = Uri.parse(args.photoUrl)
//        drawerHeaderBinding.txEmail.text = args.userEmail
//        drawerHeaderBinding.txDisplayName.text = args.displayName
//        if (imageUri != null) {
//            drawerHeaderBinding.ivAvatar.setImageURI(null)
//            drawerHeaderBinding.ivAvatar.setImageURI(imageUri)
//        }
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
            when (it.itemId) {
                R.id.home -> Toast.makeText(requireContext(), "Clicked Home", Toast.LENGTH_SHORT)
                    .show()
                R.id.profile -> Toast.makeText(requireContext(), "Clicked Profile", Toast.LENGTH_SHORT)
                    .show()
                R.id.inspirations -> vm.navigateTo(ViewPagerToMyInspirationScreenKey())
                R.id.settings -> Toast.makeText(requireContext(), "Clicked settings", Toast.LENGTH_SHORT)
                    .show()
                R.id.logout -> Toast.makeText(requireContext(), "Clicked logout", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }

    }
}