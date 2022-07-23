package com.example.learningmanager.fragments.notesmanager.adapters

import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.learningmanager.R
import com.example.learningmanager.databinding.ActivityMainBinding.bind
import com.example.learningmanager.databinding.NoteItemLayoutBinding
import com.example.learningmanager.fragments.ViewPagerFragmentDirections
import com.example.learningmanager.fragments.notesmanager.data.NoteData
import com.example.learningmanager.fragments.notesmanager.ui.NotesManagerFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.MarkwonVisitor
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin
import org.commonmark.node.SoftLineBreak

class RvNotesAdapter: ListAdapter<NoteData, RvNotesAdapter.NotesViewHolder>(DiffUtilCallback()) {

    var items = emptyList<NoteData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteItemLayoutBinding.inflate(inflater, parent, false)
            .let(::NotesViewHolder)
        setListData(items)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int): Unit = with(holder) {
        itemView
        bind(items[position])
    }

    inner class NotesViewHolder(val layout: NoteItemLayoutBinding) : RecyclerView.ViewHolder(layout.root) {
        fun bind(item: NoteData) = with(layout) {

            val title: MaterialTextView = layout.noteItemTitle
            val content: TextView = layout.noteContentItem
            val date: MaterialTextView = layout.noteDate
            val parent: MaterialCardView = layout.noteItemLayoutParent
            val markWon = Markwon.builder(itemView.context)
                .usePlugin(StrikethroughPlugin.create())
                .usePlugin(TaskListPlugin.create(itemView.context))
                .usePlugin(object : AbstractMarkwonPlugin() {
                    override fun configureVisitor(builder: MarkwonVisitor.Builder) {
                        super.configureVisitor(builder)
                        builder.on(
                            SoftLineBreak::class.java
                        ) { visitor, _ -> visitor.forceNewLine() }
                    }
                })
                .build()

            getItem(position).let { noteData ->

                parent.transitionName = "recyclerView_${item.id}"
                title.text = item.title
                markWon.setMarkdown(content, item.content)
                date.text = item.date
                parent.setBackgroundColor(item.color)
            }
            itemView.setOnClickListener {
// TODO navigation through vievmodel not recyclerview, here only trigger
                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2()
                    .setNote(item)
                val extras = FragmentNavigatorExtras(parent to "recycler_View_${item.id}")
                //hidekeyboard
                Navigation.findNavController(it).navigate(action, extras)

            }
            content.setOnClickListener {
                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2()
                    .setNote(item)
                val extras = FragmentNavigatorExtras(parent to "recycler_View_${item.id}")
                //hidekeyboard
                Navigation.findNavController(it).navigate(action, extras)
            }
        }
    }
//        getItem(position).let {noteData ->
//        holder.apply {

//            parent.transitionName="recyclerView_${noteData.id}"
//            title.text = noteData.title
//            markWon.setMarkdown(content, noteData.content)
//            date.text=noteData.date
//            parent.setBackgroundColor(noteData.color)

//            itemView.setOnClickListener {
//
//                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2()
//                    .setNote(noteData)
//                val extras = FragmentNavigatorExtras(parent to "recycler_View_${noteData.id}")
//                //hidekeyboard
//                Navigation.findNavController(it).navigate(action, extras)
//
//            }
//            content.setOnClickListener {
//                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToSaveUpdateFragment2()
//                    .setNote(noteData)
//                val extras = FragmentNavigatorExtras(parent to "recycler_View_${noteData.id}")
//                //hidekeyboard
//                Navigation.findNavController(it).navigate(action, extras)
//            }
//        }
//        }
//    }

    fun getPositionAt(position: Int): NoteData {
        return items[position]
    }

    fun setListData(list: List<NoteData>) {
        this.items = emptyList()
        this.items = list
        notifyDataSetChanged()
    }


}