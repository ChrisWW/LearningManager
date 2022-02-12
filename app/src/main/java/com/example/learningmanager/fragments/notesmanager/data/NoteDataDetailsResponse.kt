package com.example.learningmanager.fragments.notesmanager.data

data class NoteDataDetailsResponse (
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val color: Int
)