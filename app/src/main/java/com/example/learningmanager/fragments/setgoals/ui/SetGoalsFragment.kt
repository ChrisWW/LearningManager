package com.example.learningmanager.fragments.setgoals.ui

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
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
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class SetGoalsFragment @Inject constructor() :
    BaseFragment<FragmentSetGoalsBinding, SetGoalsViewModel>(
        FragmentSetGoalsBinding::inflate
    ) {
    override val vm: SetGoalsViewModel by viewModels()
    private val setGoalsAdapter: SetGoalsAdapter by initItemsAdapter()
    private val mutableStateAdapterFlow = MutableStateFlow(-1)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setGoalsAdapter = SetGoalsAdapter(vm::deleteItem, vm::onItemClicked)
//        backgroundAnimation()

        initItemsRecyclerView()
        collectGoalsItems()
        onFabClicked()
        onAcceptDeclineTodayWork()
        onAddDayWork()
        onMinusDayWork()
        collectSearchInit()
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
        vm.triggerAcceptDeclineButton.collectWith(viewLifecycleOwner) { value ->
            if (value.isSelected()) {
                CustomSetGoalsDialog(requireContext(), id).show(
                    "Save your task: ${value.title}",
                    "Clicking NO - means you haven't done your work and one additional day could be added to complete your goal depends on your preferences."
                ) {
                    onCustomSetGoalsDialogResult(it.name, value.id, value.title, value.data)
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

    private fun onAddDayWork() {
        vm.triggerAddDeclineButton.collectWith(viewLifecycleOwner) { value ->
            if (value.isSelected()) {
                val updatedData = (value.data.toInt() + 1).toString()
                vm.updateGoalData(value.id, updatedData)
                mutableStateAdapterFlow.value = value.id
            }
            vm.triggerAddDeclineButton.value = CustomSetGoalsDialogData.empty
            collectGoalsItems()
            setGoalsAdapter.notifyDataSetChanged()
        }

    }

    private fun onMinusDayWork() {
        vm.triggerMinusDeclineButton.collectWith(viewLifecycleOwner) { value ->
            if (value.isSelected()) {
                val updatedData = (value.data.toInt() - 1).toString()
                vm.updateGoalData(value.id, updatedData)
                mutableStateAdapterFlow.value = value.id
            }
            vm.triggerMinusDeclineButton.value = CustomSetGoalsDialogData.empty
            collectGoalsItems()
            setGoalsAdapter.notifyDataSetChanged()
        }
    }

    private fun onCustomSetGoalsDialogResult(
        response: String,
        id: Int,
        title: String,
        data: String
    ) {
        if (response == CustomSetGoalsDialog.ResponseType.YES.name) {
            Log.d("onCustomSetGoalsDialogResult", "TRIGGERED YES")
            // nothing happen, nothing added
        }
        if (response == CustomSetGoalsDialog.ResponseType.NO.name) {
            Log.d("onCustomSetGoalsDialogResult", "TRIGGERED NO")
            val updatedData = (data.toInt() + 1).toString()
            mutableStateAdapterFlow.value = id
            vm.updateGoalData(id, updatedData)
            collectGoalsItems()
            setGoalsAdapter.notifyDataSetChanged()
        }
        if (response == CustomSetGoalsDialog.ResponseType.CANCEL.name) {
            Log.d("onCustomSetGoalsDialogResult", "TRIGGERED CANCEL")
            // nothing happen
        }
    }

    private fun collectSearchInit() {
        layout.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                layout.noData.isVisible = false
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    val text = s.toString()
                    val query = "%$text%"
                    if (query.isNotEmpty()) {
                        collectSearchItems(query)
                    } else {
                        collectGoalsItems()
                        vm.searchGoalsDataList.value = emptyList()
                        // obs
                    }
                } else {
                    collectGoalsItems()
                    vm.searchGoalsDataList.value = emptyList()
                    //obs
                }
            }


            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun collectSearchItems(query: String) {
        vm.searchGoals(query)
        vm.searchGoalsDataList.collectWith(viewLifecycleOwner) {
            if(it.isEmpty()) {
            }
            else {
                setGoalsAdapter.items = it
                setGoalsAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mutableStateAdapterFlow.value = -1
    }

    private fun initItemsAdapter() = lazy {
        SetGoalsAdapter(
            vm::deleteItem,
            vm::onItemClicked,
            vm::onItemButtonQuestionClicked,
            vm::onItemMinusClicked,
            vm::onItemAddClicked,
            mutableStateAdapterFlow
        )
    }
// TODO
//    // Declare the launcher at the top of your Activity/Fragment:
//    private val requestPermissionLauncher = registerForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) { isGranted: Boolean ->
//        if (isGranted) {
//            // FCM SDK (and your app) can post notifications.
//        } else {
//            // TODO: Inform user that that your app will not show notifications.
//        }
//    }
//
//    private fun askNotificationPermission() {
//        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS) ==
//            PackageManager.PERMISSION_GRANTED
//        ) {
//            // FCM SDK (and your app) can post notifications.
//        } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//            // TODO: display an educational UI explaining to the user the features that will be enabled
//            //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//            //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//            //       If the user selects "No thanks," allow the user to continue without notifications.
//        } else {
//            // Directly ask for the permission
//            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//        }
//    }
}
