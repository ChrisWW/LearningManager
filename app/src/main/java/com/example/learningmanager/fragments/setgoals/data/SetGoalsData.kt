package com.example.learningmanager.fragments.setgoals.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "setgoalsdata")
data class SetGoalsData(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("goal") val goal: String = "",
    @SerializedName("intensegoal") val intenseGoal: String = "0",
    @SerializedName("timegoal") val timeGoal: String = "0",
    @SerializedName("editdate") val editDate: String = "",
    @SerializedName("initialdate") val initialDate: String = "",
    @SerializedName("color") val color: Int = -1,
    @SerializedName("expanded") var expanded: Boolean = false,
    @SerializedName("eventgoogleid") var eventGoogleId: Int = -1,
    @SerializedName("isDone") var isDone: Boolean = false
) : Serializable