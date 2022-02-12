package com.example.learningmanager.fragments.notesmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "notedata")
data class NoteData(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("date") val date: String = "",
    @SerializedName("color") val color: Int = -1
) : Serializable

