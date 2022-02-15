package com.example.learningmanager.fragments.setgoals.data.local

import androidx.room.*
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.setgoals.data.SetGoalsData

@Dao
abstract class SetGoalsDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addGoal(goal: SetGoalsData)

    @Query("SELECT * FROM setgoalsdata ORDER BY id")
    abstract suspend fun getAllGoals() : List<SetGoalsData>

    @Delete
    abstract suspend fun deleteGoal(goal: SetGoalsData)

}