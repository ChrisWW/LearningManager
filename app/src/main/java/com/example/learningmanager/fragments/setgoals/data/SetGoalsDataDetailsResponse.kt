package com.example.learningmanager.fragments.setgoals.data

data class SetGoalsDataDetailsResponse(
    val id: Int,
    val goal: String,
    val intenseGoal: Int,
    val timeGoal: Int,
    val date: String,
    val color: Int
)