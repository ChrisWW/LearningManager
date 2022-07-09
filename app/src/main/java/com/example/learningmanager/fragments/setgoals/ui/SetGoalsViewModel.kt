package com.example.learningmanager.fragments.setgoals.ui

import android.app.Dialog
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.setgoals.data.CustomSetGoalsDialogData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData
import com.example.learningmanager.fragments.setgoals.domain.GetGoalsItemDetailsUseCase
import com.example.learningmanager.fragments.setgoals.helpers.CustomSetGoalsDialog
import com.example.learningmanager.fragments.setgoals.route.SetGoalsToSaveScreenKey
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetGoalsViewModel @Inject constructor(
    private val getGoalsItemDetailsUseCase: GetGoalsItemDetailsUseCase
) : BaseViewModel() {
    val setGoalsData = MutableStateFlow<List<SetGoalsData>>(emptyList())
    val triggerAcceptDeclineButton = MutableStateFlow<CustomSetGoalsDialogData>(CustomSetGoalsDialogData(-1, ""))
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

    fun onItemButtonQuestionClicked(customSetGoalsDialogData: CustomSetGoalsDialogData) {
        triggerAcceptDeclineButton.value = CustomSetGoalsDialogData(customSetGoalsDialogData.id, customSetGoalsDialogData.title)
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