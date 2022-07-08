package com.example.learningmanager.fragments.myinspiration.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ext.collectWith
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentMyInspirationBinding
import com.example.learningmanager.databinding.ItemHeaderBinding
import com.example.learningmanager.fragments.ViewPagerToMyInspirationScreenKey
import com.example.learningmanager.fragments.myinspiration.adapters.MyInspirationAdapter
import com.example.learningmanager.fragments.myinspiration.route.MyInspirationToMyInspirationSave
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyInspirationFragment @Inject constructor() : BaseFragment<FragmentMyInspirationBinding, MyInspirationViewModel>(FragmentMyInspirationBinding::inflate) {
    override val vm: MyInspirationViewModel by viewModels()
//    private val args: MyInspirationFragmentArgs by navArgs()
    private val myInspirationAdapter: MyInspirationAdapter by initItemsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initItemsRecyclerView()
        collectGoalsItems()
    }

    private fun initItemsAdapter() = lazy {
        MyInspirationAdapter(vm::deleteItem)
    }

    private fun initItemsRecyclerView() {
        layout.rvInspirations.adapter = myInspirationAdapter
    }

    private fun collectGoalsItems() {
        vm.getActualState()
        vm.myInspirationDataList.collectWith(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Log.d("values", "$it")
                myInspirationAdapter.items = it
                myInspirationAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initUI() {
        navigateToInspirationSave()
    }

    private fun navigateToInspirationSave() {
        layout.addInspirationFab.setOnClickListener {
            vm.navigateTo(MyInspirationToMyInspirationSave())
        }
    }
// TODO
//    override fun onDestroyView() {
    //        layout.rvInspirations.adapter = null
//        super.onDestroyView()
//    }
}