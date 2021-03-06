package com.example.learningmanager.fragments.auth.ui

import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.auth.route.SingleModeAuthScreenKey
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingleModeAuthViewModel @Inject constructor() : BaseViewModel() {

    fun navigateToViewPagerFragment(
        userEmail: String,
        displayName: String,
        photoUrl: String
    ) {
        navigateTo(
            SingleModeAuthScreenKey(
                userEmail,
                displayName,
                photoUrl
            )
        )
    }
}