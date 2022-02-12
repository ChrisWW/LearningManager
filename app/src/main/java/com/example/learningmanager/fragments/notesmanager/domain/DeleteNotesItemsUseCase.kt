package com.example.learningmanager.fragments.notesmanager.domain

import com.example.learningmanager.base.domain.BaseUseCase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import javax.inject.Inject

class DeleteNotesItemsUseCase @Inject constructor(private val noteDataDao: NoteDataDao):
    BaseUseCase<NoteData, Unit>() {
    public override suspend fun create(params: NoteData){
        noteDataDao.deleteNote(params)
    }
}
