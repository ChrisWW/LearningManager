package com.example.learningmanager.base.ui

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize


sealed class Alert : Parcelable {
    @Parcelize
    data class Native(
        @StringRes val messageRes: Int,
        @StringRes val title: Int,
        @StringRes val positiveButtonTitle: Int,
        @StringRes val negativeButtonTitle: Int,
        val onPositiveButtonPressed: () -> Unit = {},
        val onNegativeButtonPressed: () -> Unit = {}
    ) : Alert()

    @Parcelize
    data class Snackbar(
        @StringRes val messageRes: Int
    ) : Alert()
}