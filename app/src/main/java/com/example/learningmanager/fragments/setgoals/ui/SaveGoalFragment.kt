package com.example.learningmanager.fragments.setgoals.ui

import android.content.ContentResolver
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
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
                Toast.makeText(activity, "Not all the data is covered...", Toast.LENGTH_SHORT).show()
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
            }
            vm.navigateBack()
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
        if(layout.cbSaveInGoogleCalendar.isActivated) {
            val contentResolver = object: ContentResolver(context) {}
            vm.saveInGoogleCalendar(layout.etGoal.text.toString(), contentResolver)
        }
    }

    private fun sendTaskToEmail() {
        if(layout.cbSendTaskToEmail.isActivated) {
            vm.sendTaskToEmail()
        }
    }

    private fun getActualTimeEpoch(): String {
        val actualEpoch = Calendar.getInstance().timeInMillis / 1000
        return actualEpoch.toString()
    }
}