package com.example.learningmanager.fragments.myinspiration.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "myinspiration")
data class MyInspirationData(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("imageUrl") val imageUrl: String = "",
    @SerializedName("date") val date: String = "",
    @SerializedName("authorQuote") val authorQuote: String = "",
    @SerializedName("quote") val quote: String = "",
    @SerializedName("localization") val localization: String = "",
) : Serializable
