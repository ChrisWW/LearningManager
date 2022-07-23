package com.example.learningmanager.fragments.myinspiration.data.local

import androidx.room.*
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationData

@Dao
abstract class MyInspirationDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addInspiration(inspirationData: MyInspirationData)

    @Query("SELECT * FROM myinspiration ORDER BY id DESC")
    abstract suspend fun getAllInspirations(): List<MyInspirationData>
}