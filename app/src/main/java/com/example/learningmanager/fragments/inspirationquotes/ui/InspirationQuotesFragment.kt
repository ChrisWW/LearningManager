package com.example.learningmanager.fragments.inspirationquotes.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentInspirationQuotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest

@AndroidEntryPoint
class InspirationQuotesFragment :
    BaseFragment<FragmentInspirationQuotesBinding, InspirationQuotesViewModel>
        (FragmentInspirationQuotesBinding::inflate) {
    override val vm: InspirationQuotesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // dodac state internet connection

        backgroundAnimation()
        Log.d("animation", "Background onViewcreated")
//        loadImage()
        loadQuoteWithImage()
        onFloatingActionClick()
    }

    override fun onResume() {
        super.onResume()

        //too many times
        layout.dotsLoading.initView()
        Log.d("animation", "Background onResume")


    }

    private fun loadQuoteWithImage() {
        layout.floatingActionBtn.setOnClickListener {
            onFloatingActionClick()
        }
    }

    private fun onFloatingActionClick() {
        layout.floatingActionBtn.animate().apply {
            rotationBy(360f)
            duration = 1000
        }.start()
        layout.ivImageQuotes.visibility = View.GONE
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

    private fun makeApiRequest() {
        layout.dotsLoading.visibility = View.VISIBLE
        var authorName = ""
        lifecycleScope.launch {
            vm.authorAndQuote
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { response ->
                    authorName = response.quoteAuthor
                    layout.tvAuthor.text = response.quoteAuthor
                    layout.tvQuote.text = response.quoteText
                    if (authorName != "") {
                        vm.getImageAuthorFromWikipedia(authorName)
                            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                            .collectLatest { response2 ->
                                if (response2 != null && response2.query != null && response2.query.pages != null && response2.query.pages.entries.first() != null && response2.query.pages.entries.first().value.thumbnail != null) {
                                    Glide.with(requireContext())
                                        .load(response2.query.pages.entries.first().value.thumbnail!!.source)
                                        .apply(RequestOptions().override(1100,1800))
                                        .centerCrop()
                                        .into(layout.ivImageQuotes)
                                    layout.ivImageQuotes.visibility = View.VISIBLE
                                    layout.dotsLoading.visibility = View.GONE
                                } else {
                                    Glide.with(requireContext())
                                        .load(ContextCompat.getDrawable(requireContext(), R.drawable.offline)
                                    )
                                        .apply(RequestOptions().override(1000,1600))
                                        .centerCrop()
                                        .into(layout.ivImageQuotes)
                                    layout.ivImageQuotes.visibility = View.VISIBLE
                                    layout.dotsLoading.visibility = View.GONE
                                }
                            }
                    } else {
                        Glide.with(requireContext())
                            .load(ContextCompat.getDrawable(requireContext(), R.drawable.offline)
                            )
                            .apply(RequestOptions().override(1000,1600))
                            .centerCrop()
                            .into(layout.ivImageQuotes)
                        layout.ivImageQuotes.visibility = View.VISIBLE
                        layout.dotsLoading.visibility = View.GONE
                    }
                }

        }
    }
}


