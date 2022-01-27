package com.example.learningmanager.fragments.notesmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentNotesManagerBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotesManagerFragment @Inject constructor() : BaseFragment<FragmentNotesManagerBinding, NotesManagerViewModel>(
    FragmentNotesManagerBinding::inflate
) {
    override val vm: NotesManagerViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}