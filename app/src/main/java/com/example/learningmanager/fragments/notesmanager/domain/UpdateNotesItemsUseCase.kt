package com.example.learningmanager.fragments.notesmanager.domain

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.base.domain.BaseUpdateUseCase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import javax.inject.Inject

class UpdateNotesItemsUseCase @Inject constructor(private val noteDataDao: NoteDataDao): BaseUpdateUseCase<NoteData, Unit>() {
    public override suspend fun create(params: NoteData){
        noteDataDao.updateNote(params)
    }
}
