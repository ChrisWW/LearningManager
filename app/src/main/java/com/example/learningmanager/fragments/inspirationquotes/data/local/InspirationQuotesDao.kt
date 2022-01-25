package com.example.learningmanager.fragments.inspirationquotes.data.local

import androidx.room.Query
import com.example.learningmanager.fragments.inspirationquotes.data.InspirationQuotesData

abstract class InspirationQuotesDao {

    @Query("SELECT * FROM inspirationquotes_items")
    abstract suspend fun getAllItems(): List<InspirationQuotesData>
}