package com.example.learningmanager.fragments.setgoals.data

data class SetGoalsDataDetailsResponse(
    val id: Int,
    val goal: String,
    val intenseGoal: String,
    val timeGoal: String,
    val date: String,
    val color: Int
)