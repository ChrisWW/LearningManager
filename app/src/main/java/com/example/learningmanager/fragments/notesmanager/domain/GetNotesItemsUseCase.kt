package com.example.learningmanager.fragments.notesmanager.domain

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesItemsUseCase @Inject constructor(private val noteDataDao: NoteDataDao): BaseFlowUseCase<Unit, List<NoteData>>() {
    override fun create(params: Unit): Flow<List<NoteData>> {
        return flow{
            emit(noteDataDao.getAllNote())
        }
    }
}