package com.example.learningmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.learningmanager.fragments.inspirationquotes.ui.InspirationQuotesFragment
import com.example.learningmanager.fragments.notesmanager.ui.NotesManagerFragment
import com.example.learningmanager.fragments.setgoals.ui.SetGoalsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}