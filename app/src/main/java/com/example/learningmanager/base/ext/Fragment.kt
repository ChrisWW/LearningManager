package com.example.learningmanager.base.ext

import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment

//inline fun <F : Fragment> F.withArguments(block: Bundle.() -> Unit): F = apply {
//    arguments = Bundle().apply(block)
//}
//
//fun Fragment.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
//    requireContext().showToast(message, duration)
//}
//
//fun Fragment.hideSoftKeyboard() {
//    activity?.hideSoftKeyboard()
//}
//
//inline fun Fragment.runOnBackPressed(crossinline block: () -> Unit) {
//    requireActivity().onBackPressedDispatcher.addCallback(this) {
//        block()
//        isEnabled = false
//        requireActivity().onBackPressed()
//    }
//}