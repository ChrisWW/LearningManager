package com.example.learningmanager.inspirationquotes.data.local

import androidx.room.Query
import com.example.learningmanager.inspirationquotes.data.InspirationQuotesData

abstract class InspirationQuotesDao {

    @Query("SELECT * FROM inspirationquotes_items")
    abstract suspend fun getAllItems(): List<InspirationQuotesData>
}