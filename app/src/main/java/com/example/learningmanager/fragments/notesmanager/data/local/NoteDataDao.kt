package com.example.learningmanager.fragments.notesmanager.data.local

import androidx.room.*
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NoteDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addNote(noteData: NoteData)

    @Update
    abstract suspend fun updateNote(noteData: NoteData)

    @Query("SELECT * FROM notedata ORDER BY id DESC")
    abstract suspend fun getAllNote(): List<NoteData>

    @Query("SELECT * FROM notedata WHERE title LIKE :query OR content LIKE :query OR date LIKE :query ORDER BY id DESC")
    abstract suspend fun searchNote(query: String): List<NoteData>

    @Delete
    abstract suspend fun deleteNote(note: NoteData)

}