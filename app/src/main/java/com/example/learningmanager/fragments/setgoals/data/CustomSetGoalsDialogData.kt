package com.example.learningmanager.fragments.setgoals.data

data class CustomSetGoalsDialogData (val id: Int, val title: String) {
    fun isEmpty(): Boolean {
        return this == empty
    }

    fun isSelected(): Boolean {
        return !isEmpty()
    }

    companion object {
        val empty = CustomSetGoalsDialogData(-1, "")
    }
}