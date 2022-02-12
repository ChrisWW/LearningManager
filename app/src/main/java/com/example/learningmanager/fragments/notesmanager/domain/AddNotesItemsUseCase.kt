package com.example.learningmanager.fragments.notesmanager.domain

import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.NoteDataDetailsResponse
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import javax.inject.Inject

class AddNotesItemsUseCase @Inject constructor(private val noteDataDao: NoteDataDao) {
    suspend fun create(params: NoteDataDetailsResponse) {
        val noteData = NoteData(params.id, params.title, params.content, params.date, params.color
        )
        noteDataDao.addNote(noteData)
    }
}