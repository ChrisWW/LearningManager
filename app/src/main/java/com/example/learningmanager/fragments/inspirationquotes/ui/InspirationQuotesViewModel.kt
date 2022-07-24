package com.example.learningmanager.fragments.inspirationquotes.ui

import android.app.Application
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.data.remote.WikipediaPageDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.domain.PostInspirationQuote
import com.example.learningmanager.fragments.inspirationquotes.domain.GetImageQuotePictureItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InspirationQuotesViewModel @Inject constructor(
    application: Application,
    private val postInspirationQuote: PostInspirationQuote,
    private val getImageQuotePictureItemUseCase: GetImageQuotePictureItemUseCase
) : BaseViewModel() {
    // is it okay with ? and null
//    var imageAuthorFromWikipedia: Flow<WikipediaPageDetailsResponse> =
//        getImageQuotePictureItemUseCase.build("SaintInspiration", Unit)
    var authorAndQuote: Flow<InspirationQuotesDetailsResponse> = postInspirationQuote.build(Unit)

    fun getImageAuthorFromWikipedia(name: String): Flow<WikipediaPageDetailsResponse> {
        return getImageQuotePictureItemUseCase.build(name, Unit)
    }
}