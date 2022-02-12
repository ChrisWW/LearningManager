package com.example.learningmanager.fragments.notesmanager.domain

import com.example.learningmanager.base.domain.BaseFlowUseCase
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.local.NoteDataDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchNotesItemUseCase @Inject constructor(private val noteDataDao: NoteDataDao) :
    BaseFlowUseCase<String, List<NoteData>>() {
    public override fun create(query: String): Flow<List<NoteData>> {
        return flow {
            emit(noteDataDao.searchNote(query))
        }
    }
}