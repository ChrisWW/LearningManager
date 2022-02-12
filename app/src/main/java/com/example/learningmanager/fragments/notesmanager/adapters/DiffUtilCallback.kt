package com.example.learningmanager.fragments.notesmanager.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.learningmanager.fragments.notesmanager.data.NoteData

class DiffUtilCallback : DiffUtil.ItemCallback<NoteData>() {

    override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean {
        return oldItem.id==newItem.id
    }

}
