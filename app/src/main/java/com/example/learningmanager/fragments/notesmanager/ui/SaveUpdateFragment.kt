package com.example.learningmanager.fragments.notesmanager.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.BottomSheetLayoutBinding

import com.example.learningmanager.databinding.FragmentSaveUpdateBinding
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.data.NoteDataDetailsResponse
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SaveUpdateFragment @Inject constructor() :
    BaseFragment<FragmentSaveUpdateBinding, NotesManagerViewModel>(
        FragmentSaveUpdateBinding::inflate
    ) {
    override val vm: NotesManagerViewModel by activityViewModels()
    private lateinit var navController: NavController
    private val currentData = SimpleDateFormat.getInstance().format(Date())
    private var note: NoteData? = null
    private var result = ""

    private var color = -1
    private val args: SaveUpdateFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragmentContainerView
            scrimColor = Color.TRANSPARENT
            duration = 300L
        }
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            activity!!.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        ViewCompat.setTransitionName(
            layout.noteContentFragmentParent, "recyclerView_${args.note?.id}"
        )

        layout.backBtn.setOnClickListener {
            navController.popBackStack()
        }

        layout.saveNote.setOnClickListener {
            saveNote()
        }
        try {
            layout.etNoteContent.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    layout.bottomBar.visibility = View.VISIBLE
                    layout.etNoteContent.setStylesBar(layout.styleBar)
                } else layout.bottomBar.visibility = View.GONE

            }
        } catch (e: Throwable) {
            Log.d("TAG", "${e.stackTrace}")
        }
            layout.fabColorPick.setOnClickListener {
                Log.d("bottom", "LogujeBottomfabCOlorpoickFirst")
                val bottomSheetDialog = BottomSheetDialog(
                    requireContext(),
                    R.style.BottomSheetDialogTheme
                )

                val bottomSheetView: View = layoutInflater.inflate(
                    R.layout.bottom_sheet_layout,
                    null
                )
                bottomSheetView.post {
                    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
                with(bottomSheetDialog)
                {
                    bottomSheetView.visibility = View.VISIBLE
                    setCanceledOnTouchOutside(false)
                    setContentView(bottomSheetView)
                    Log.d("bottom", "LogujeBottom11")
                    show()
                }
                bottomSheetDialog.show()

                val bottomSheetBinding = BottomSheetLayoutBinding.bind(bottomSheetView)
                bottomSheetBinding.apply {
                    colorPicker.apply {
                        Log.d("bottom", "LogujeBottom")
                        setSelectedColor(color)
                        setOnColorSelectedListener { value ->
                            color = value
                            layout.apply {
                                noteContentFragmentParent.setBackgroundColor(color)
                                toolbarFragmentNoteContent.setBackgroundColor(color)
                                bottomBar.setBackgroundColor(color)
                                activity!!.window.statusBarColor = color
                            }
                            bottomSheetBinding.bottomSheetParent.setCardBackgroundColor(color)
                        }
                    }
                    bottomSheetParent.setCardBackgroundColor(color)
                }
                bottomSheetView.post {
                    bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            //open with existing note item
            setUpNote()
    }

    private fun setUpNote() {
        val note = args.note
        val title = layout.etTitle
        val content = layout.etNoteContent
        val lastEdited = layout.lastEdited

        if (note == null) {
            layout.lastEdited.text =
                getString(R.string.edited_on, SimpleDateFormat.getDateInstance().format(Date()))
        }
        if (note != null) {
            title.setText(note.title)
            content.renderMD(note.content)
            lastEdited.text = getString(R.string.edited_on, note.date)
            color = note.color
            layout.apply {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(10)
                    noteContentFragmentParent.setBackgroundColor(color)
                }
                toolbarFragmentNoteContent.setBackgroundColor(color)
                bottomBar.setBackgroundColor(color)
            }
            activity?.window?.statusBarColor = note.color
        }
    }

    private fun saveNote() {

        if (layout.etNoteContent.text.toString().isEmpty() || layout.etTitle.text.toString()
                .isEmpty()
        ) {
            Toast.makeText(activity, "Something is Empty", Toast.LENGTH_SHORT).show()
        } else {
            note = args.note
            when (note) {
                null -> {
                    vm.saveNote(
                        NoteDataDetailsResponse(
                            0,
                            layout.etTitle.text.toString(),
                            layout.etNoteContent.getMD(),
                            currentData,
                            color
                        )
                    )
                    // moze niepotrzebne we can use flow
                    result = "Note Saved"
                    parentFragmentManager.setFragmentResult(
                        "key",
                        bundleOf("bundleKey" to result)
                    )

                    Log.d("note", "$note")
//                    navController.navigate(SaveUpdateFragmentDirections.actionSaveUpdateFragment2ToViewPagerFragment())
//
                    vm.navigateBack()
                }
                else -> {
                    updateNote()
                    vm.navigateBack()
                }
            }
        }
    }

    private fun updateNote() {
        if (note != null) {
            vm.updateNote(
                NoteData(
                    note!!.id,
                    layout.etTitle.text.toString(),
                    layout.etNoteContent.getMD(),
                    currentData,
                    color
                )
            )

        }
    }
}