package com.example.learningmanager.fragments.inspirationquotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO niepotrzebne? lub do cachowanie

@Entity(tableName = "inspirationquotes_items")
data class InspirationQuotesData(
    @PrimaryKey val id: String,
    val quoteAuthor: String,
    val quoteText: String
)
