package com.example.learningmanager.fragments.setgoals.ui

import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.data.CustomSetGoalsDialogData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.domain.GetGoalsItemDetailsUseCase
import com.example.learningmanager.fragments.setgoals.domain.UpdateDateGoalElementItemUseCase
import com.example.learningmanager.fragments.setgoals.route.SetGoalsToSaveScreenKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetGoalsViewModel @Inject constructor(
    private val getGoalsItemDetailsUseCase: GetGoalsItemDetailsUseCase,
    private val searchGoalsItemUseCase: SearchGoalsItemUseCase,
    private val updateDateGoalElementItemUseCase: UpdateDateGoalElementItemUseCase,
) : BaseViewModel() {
    val setGoalsData = MutableStateFlow<List<SetGoalsData>>(emptyList())
    val searchGoalsDataList = MutableStateFlow<List<SetGoalsData>>(emptyList())
    val triggerAcceptDeclineButton =
        MutableStateFlow<CustomSetGoalsDialogData>(CustomSetGoalsDialogData(-1, "", ""))
    val triggerMinusDeclineButton = MutableStateFlow(CustomSetGoalsDialogData(-1, "", ""))
    val triggerAddDeclineButton = MutableStateFlow(CustomSetGoalsDialogData(-1, "", ""))


    init {
        getActualState()
    }

    fun getActualState() {
        viewModelScope.launch {
            getGoalsItemDetailsUseCase.build(Unit).collect {
                setGoalsData.value = it
            }
        }
    }

    fun updateGoalData(id: Int, date: String) {
        viewModelScope.launch {
            updateDateGoalElementItemUseCase.build(id, date)
        }
    }

    fun onNavigateToSave() {
        navigateTo(SetGoalsToSaveScreenKey())
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            //usecase with id
            getActualState()
        }
    }

    fun onItemClicked(id: Int) {
        // navigaton to root
    }

    fun onItemShowDialogBtn(title: String, id: Int) {

    }

    fun searchGoals(query: String) {
        viewModelScope.launch {
            searchGoalsItemUseCase.create(query).collectLatest {
                searchGoalsDataList.value = it
            }
        }
    }

    fun onItemButtonQuestionClicked(customSetGoalsDialogData: CustomSetGoalsDialogData) {
        triggerAcceptDeclineButton.value = CustomSetGoalsDialogData(
            customSetGoalsDialogData.id,
            customSetGoalsDialogData.title,
            customSetGoalsDialogData.data
        )
    }

    fun onItemMinusClicked(customSetGoalsDialogData: CustomSetGoalsDialogData) {
        triggerMinusDeclineButton.value = CustomSetGoalsDialogData(
            customSetGoalsDialogData.id,
            customSetGoalsDialogData.title,
            customSetGoalsDialogData.data
        )
    }

    fun onItemAddClicked(customSetGoalsDialogData: CustomSetGoalsDialogData) {
        triggerAddDeclineButton.value = CustomSetGoalsDialogData(
            customSetGoalsDialogData.id,
            customSetGoalsDialogData.title,
            customSetGoalsDialogData.data
        )
    }
}
//    private fun showDialog(title: String) {
//        val dialog = Dialog(activity)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_layout)
//        val body = dialog.findViewById(R.id.body) as TextView
//        body.text = title
//        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
//        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
//        yesBtn.setOnClickListener {
//            dialog.dismiss()
//        }
//        noBtn.setOnClickListener { dialog.dismiss() }
//        dialog.show()
//
//    }