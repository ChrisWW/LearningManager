package com.example.learningmanager.fragments.myinspiration.data

data class MyInspirationDetailsResponse (
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val authorQuote: String,
    val quote: String,
    val localization: String
)