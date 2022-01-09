package com.example.learningmanager.inspirationquotes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inspirationquotes_items")
data class InspirationQuotesData(
    @PrimaryKey val id: String,
    val fileSizeBytes: Int,
    val urlPicture: String,
    val quote: String
)
