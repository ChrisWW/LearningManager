package com.example.learningmanager.fragments.notesmanager.ui

import androidx.lifecycle.viewModelScope
import android.app.Application
import android.util.Log
import com.example.learningmanager.base.ui.BaseViewModel
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesDetailsResponse
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.NoteDataDetailsResponse
import com.example.learningmanager.fragments.notesmanager.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesManagerViewModel @Inject constructor(
    private val application: Application,
    private val addNotesItemsUseCase: AddNotesItemsUseCase,
    private val deleteNotesItemsUseCase: DeleteNotesItemsUseCase,
    private val getNotesItemsUseCase: GetNotesItemsUseCase,
    private val searchNotesItemUseCase: SearchNotesItemUseCase,
    private val updateNotesItemsUseCase: UpdateNotesItemsUseCase
) : BaseViewModel() {
    val noteDataList = MutableStateFlow<List<NoteData>>(emptyList())
    val searchDataList = MutableStateFlow<List<NoteData>>(emptyList())

    init {
        getActualState()
    }
    fun getActualState() {
        viewModelScope.launch {
            getNotesItemsUseCase.build(Unit).collect {
                noteDataList.value = it
                Log.d("newNoteData", "${noteDataList.value}")
            }
        }
    }

    fun updateNote(existingNoteData: NoteData) {
        viewModelScope.launch {
            updateNotesItemsUseCase.create(existingNoteData)
            getActualState()
        }
    }

    fun deleteNote(existingNoteData: NoteData) {
        viewModelScope.launch {
            deleteNotesItemsUseCase.create(existingNoteData)
            getActualState()
        }
    }
    fun saveNote(newNoteData: NoteDataDetailsResponse) {
        viewModelScope.launch {
            addNotesItemsUseCase.create(newNoteData)
            getActualState()
            Log.d("newNoteData", "$newNoteData")
        }
    }

    fun searchNote(query: String) {
        viewModelScope.launch {
            searchNotesItemUseCase.create(query).collectLatest {
                searchDataList.value = it
                Log.d("FromVM", "${searchDataList.value}")
            }
        }
    }
}
