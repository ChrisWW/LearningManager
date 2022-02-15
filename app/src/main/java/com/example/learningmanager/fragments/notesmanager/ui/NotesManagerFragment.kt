package com.example.learningmanager.fragments.notesmanager.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.learningmanager.R
import com.example.learningmanager.base.ext.collectWith
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentNotesManagerBinding
import com.example.learningmanager.fragments.ViewPagerFragmentDirections
import com.example.learningmanager.fragments.notesmanager.adapters.RvNotesAdapter
import com.example.learningmanager.fragments.notesmanager.data.NoteDataDetailsResponse
import com.example.learningmanager.fragments.notesmanager.utils.SwipeToDelete
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class NotesManagerFragment @Inject constructor() :
    BaseFragment<FragmentNotesManagerBinding, NotesManagerViewModel>(
        FragmentNotesManagerBinding::inflate
    ) {
    override val vm: NotesManagerViewModel by viewModels()
    private lateinit var rvNotesAdapter: RvNotesAdapter

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = 350
        }
        exitTransition = MaterialElevationScale(true).apply {
            duration = 350
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNotesAdapter = RvNotesAdapter()
        recyclerViewDisplay()
        collectNotesItems()
//        observerDataChanges()
        //Check this
        lifecycleScope.launch {
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            activity!!.window.statusBarColor = Color.parseColor("#9E9D9D")
        }
        // do zmiany navigation
        val navHostFragment =
            activity!!.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        layout.addNoteFab.setOnClickListener {
            layout.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2())

        }

        layout.innerFab.setOnClickListener {
            layout.appBarLayout.visibility = View.INVISIBLE
            navController.navigate(ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2())
        }

        layout.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                layout.noData.isVisible = false
            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    val text = s.toString()
                    val query = "%$text%"
                    if (query.isNotEmpty()) {
                        // livedata with observe to change
                            Log.d("query", "QUERY IS: $query")
//                        vm.searchNote(query)

                        Log.d("query", "LIST IS: ${vm.searchDataList.value}")
//                        rvNotesAdapter.submitList(vm.searchDataList.value)
                        collectSearchItems(query)

                    } else {
                        collectNotesItems()
                        vm.searchDataList.value = emptyList()
                        // obs
                    }
                } else {
                    collectNotesItems()
                    vm.searchDataList.value = emptyList()
                    //obs
                }
            }


            override fun afterTextChanged(s: Editable?) {

            }

        })

        layout.search.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.clearFocus()
                // hide keyboard
            }
            return@setOnEditorActionListener true
        }

        layout.rvNote.setOnScrollChangeListener()
        { _, scrollX, scrollY, _, oldScrollY ->

            when {
                scrollY > oldScrollY -> {
                    layout.chatFabText.isVisible = false
                }
                scrollX == scrollY -> {
                    layout.chatFabText.isVisible = true
                }
                else -> {
                    layout.chatFabText.isVisible = true
                }
            }
        }

    }

    private fun swipeToDelete(rvNote: RecyclerView) {
        val swipeToDeleteCallBack = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val positionAdapter = viewHolder.layoutPosition
                val positionRoom = rvNotesAdapter.getPositionAt(positionAdapter).id
                val note = rvNotesAdapter.currentList[positionAdapter]

                val noteResponse = NoteDataDetailsResponse(
                    id = note.id,
                    title = note.title,
                    content = note.content,
                    date = note.date,
                    color = note.color
                )

                var actionBtnTapped = false

                vm.deleteNote(note).let {
                    collectNotesItems()
                }

                layout.search.apply {
                    //hidekey
                    clearFocus()
                }
                if (layout.search.text.toString().isEmpty()) {
                    collectNotesItems()
                }
                val snackBar = Snackbar.make(
                    requireView(), "Note Deleted", Snackbar.LENGTH_LONG
                ).addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                    }

                    override fun onShown(transientBottomBar: Snackbar?) {
                        transientBottomBar?.setAction("UNDO") {
                            vm.saveNote(noteResponse)
                            actionBtnTapped = true
                            layout.noData.isVisible = false
                            // refresh data?
                        }

                        super.onShown(transientBottomBar)
                    }
                }).apply {
                    animationMode = Snackbar.ANIMATION_MODE_FADE
                    setAnchorView(R.id.add_note_fab)
                }
                snackBar.setActionTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.yellowOrange
                    )
                )
                snackBar.show()
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(rvNote)
    }

//    private fun observerDataChanges() {
//        vm.getActualState()
//        // again with observer
//        if (vm.noteDataList.value.isNotEmpty()) {
////            collectNotesItems()
//            rvNotesAdapter.submitList(vm.noteDataList.value)
////        rvNotesAdapter.notifyDataSetChanged()
//        }
//    }

    private fun initItemsRecyclerView() {
        swipeToDelete(layout.rvNote)
        layout.rvNote.adapter = rvNotesAdapter
    }

    private fun recyclerViewDisplay() {
        initItemsRecyclerView()
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> setUpRecyclerView(2)
            Configuration.ORIENTATION_LANDSCAPE -> setUpRecyclerView(3)
        }
    }

    private fun setUpRecyclerView(spanCount: Int) {
        layout.rvNote.apply {
            layoutManager =
                StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            postponeEnterTransition(300L, TimeUnit.MILLISECONDS)
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
//        collectNotesItems()
    }
    private fun collectNotesItems() {
        vm.noteDataList.collectWith(viewLifecycleOwner) {
            if(it.isEmpty()) {
                Log.d("collect", " empty $it")
//                rvNotesAdapter.notifyDataSetChanged()
                rvNotesAdapter.submitList(vm.noteDataList.value)
            } else {
                Log.d("collect", "NOT empty $it")
                rvNotesAdapter.items = it
                rvNotesAdapter.submitList(vm.noteDataList.value)
//                rvNotesAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun collectSearchItems(query: String) {
        vm.searchNote(query)
        vm.searchDataList.collectWith(viewLifecycleOwner) {
            if(it.isEmpty()) {
            }
            else {
                rvNotesAdapter.items = it
                rvNotesAdapter.submitList(it)
            }
        }
    }
}