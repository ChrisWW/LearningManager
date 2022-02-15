package com.example.learningmanager.fragments.setgoals.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "setgoalsdata")
data class SetGoalsData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("goal") val goal: String = "",
    @SerializedName("intenseGoal") val intenseGoal: Int = 0,
    @SerializedName("timeGoal") val timeGoal: Int = 0,
    @SerializedName("date") val date: String = "",
    @SerializedName("color") val color: Int = -1
) : Serializable