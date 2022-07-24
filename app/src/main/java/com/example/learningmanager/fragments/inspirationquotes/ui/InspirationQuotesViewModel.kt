package com.example.learningmanager.fragments.inspirationquotes.ui

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewModelScope
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.data.remote.WikipediaPageDetailsResponse
import com.example.learningmanager.fragments.inspirationquotes.domain.PostInspirationQuote
import com.example.learningmanager.fragments.inspirationquotes.domain.GetImageQuotePictureItemUseCase
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsFirebaseUseCase
import com.example.learningmanager.fragments.myinspiration.domain.AddMyInspirationItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InspirationQuotesViewModel @Inject constructor(
    application: Application,
    private val postInspirationQuote: PostInspirationQuote,
    private val getImageQuotePictureItemUseCase: GetImageQuotePictureItemUseCase,
    private val addMyInspirationItemsUseCase: AddMyInspirationItemsUseCase,
    private val addMyInspirationItemsFirebaseUseCase: AddMyInspirationItemsFirebaseUseCase,
) : BaseViewModel() {
    // is it okay with ? and null
//    var imageAuthorFromWikipedia: Flow<WikipediaPageDetailsResponse> =
//        getImageQuotePictureItemUseCase.build("SaintInspiration", Unit)
    var authorAndQuote: Flow<InspirationQuotesDetailsResponse> = postInspirationQuote.build(Unit)

    fun getImageAuthorFromWikipedia(name: String): Flow<WikipediaPageDetailsResponse> {
        return getImageQuotePictureItemUseCase.build(name, Unit)
    }

    fun saveFavouriteInspiration(newMyInspiration: MyInspirationDetailsResponse, activity: FragmentActivity) {
        viewModelScope.launch {
            addMyInspirationItemsUseCase.create(newMyInspiration)
            addMyInspirationItemsFirebaseUseCase.create(newMyInspiration, activity)
//            navigateBack()
        }
    }
}