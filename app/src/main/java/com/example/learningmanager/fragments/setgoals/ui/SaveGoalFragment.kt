package com.example.learningmanager.fragments.setgoals.ui

import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.example.learningmanager.MainActivity
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentSaveGoalBinding
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsDataDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SaveGoalFragment @Inject constructor() :
    BaseFragment<FragmentSaveGoalBinding, SaveGoalsViewModel>(
        FragmentSaveGoalBinding::inflate
    ) {

    override val vm: SaveGoalsViewModel by viewModels()
    private var setGoalData: SetGoalsData? = null
    private val currentData = getActualTimeEpoch()

    //    private val currentData = SimpleDateFormat.getInstance().format(Date())
    private var initialData = initDataString()
    private val color = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCalendarComeBack()
        onCalendarDataClick()
        saveInGoogleGoogleCalendar()
        sendTaskToEmail()
        onInfoClick()
        onClickBack()
        onSaveClicked()
    }


    private fun onClickBack() {
        layout.backBtn.setOnClickListener {
            vm.navigateBack()
        }
    }

    private fun onSaveClicked() {
        layout.saveGoal.setOnClickListener {
            if (layout.etGoal.text.toString().isEmpty() || layout.etHowLong.text.toString()
                    .isEmpty() || layout.etPerDay.text.toString().isEmpty()
            ) {
                Toast.makeText(activity, "Not all the data is covered...", Toast.LENGTH_SHORT)
                    .show()
            } else {
                vm.saveGoal(
                    SetGoalsDataDetailsResponse(
                        0,
                        layout.etGoal.text.toString(),
                        layout.etHowLong.text.toString(),
                        layout.etPerDay.text.toString(),
                        currentData,
                        initialData,
                        color,
                        false
                    )
                )
                saveInGoogleGoogleCalendar()
                sendTaskToEmail()
            }
            if (MainActivity.addGoogleSaveAndNavigate) {
                Log.d("TUTAJLOG", "PRZESZLO")
                vm.navigateBack()
            } else {
                vm.navigateBack()
            }
        }
    }

    private fun onCalendarDataClick() {
        vm.calendarGoal(layout.etHowLong, context)
    }

    private fun onInfoClick() {
        layout.ivInformation.setOnClickListener {
            vm.showInfo(context)
        }
    }

    private fun initDataString(): String {
        return if (initialData.isNullOrEmpty()) {
            getActualTimeEpoch()
        } else {
            initialData
        }
    }

    private fun saveInGoogleGoogleCalendar() {
        if (layout.cbSaveInGoogleCalendar.isChecked) {
//            val contentResolver = object : ContentResolver(requireActivity()) {}
//            val contentResolver: ContentResolver = requireActivity().contentResolver
            Log.d("isActivatedgoogle", "${layout.cbSaveInGoogleCalendar.isChecked}")
            vm.saveInGoogleCalendar(requireActivity(), layout.etGoal.text.toString())
        }
    }

    private fun sendTaskToEmail() {
        if (layout.cbSendTaskToEmail.isChecked) {
            vm.sendTaskToEmail(requireActivity(), "wiewiorek567@gmail.com", layout.etGoal.text.toString(), layout.etHowLong.text.toString())
        }
    }

    private fun getActualTimeEpoch(): String {
        val actualEpoch = Calendar.getInstance().timeInMillis / 1000
        return actualEpoch.toString()
    }

    private fun onCalendarComeBack() {
        if (MainActivity.addGoogleSaveAndNavigate) {
            vm.navigateBack()
        }
    }
}