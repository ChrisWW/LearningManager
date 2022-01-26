package com.example.learningmanager.fragments.inspirationquotes.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentInspirationQuotesBinding
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class InspirationQuotesFragment :
    BaseFragment<FragmentInspirationQuotesBinding, InspirationQuotesViewModel>
        (FragmentInspirationQuotesBinding::inflate) {
    override val vm: InspirationQuotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backgroundAnimation()
        getRandomPicture()
        onFloatingActionClick()
    }

    override fun onResume() {
        super.onResume()


    }

    private fun getRandomPicture() {
        layout.floatingActionBtn.setOnClickListener {
            onFloatingActionClick()
        }
    }

    private fun onFloatingActionClick() {
        layout.floatingActionBtn.animate().apply {
            rotationBy(360f)
            duration = 1000
        }.start()
        layout.ivRandomPicture.visibility = View.GONE
        makeApiRequest()
    }

    private fun backgroundAnimation() {
        val animationDrawable: AnimationDrawable = layout.rlLayout.background as AnimationDrawable

        animationDrawable.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3600)
            start()
        }
    }

    private fun makeApiRequest() = lifecycleScope.launch {
            vm.randomPicture
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { response ->
                    if (response.fileSizeBytes < 600000) {
                        Log.d("fragment", "itGetsValue")
                        Glide.with(requireContext()).load(response.url)
                            .into(layout.ivRandomPicture)
                        layout.ivRandomPicture.visibility = View.VISIBLE
                    } else {
                        onFloatingActionClick()
                    }
                }
    }

}

