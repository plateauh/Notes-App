package com.najed.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.databinding.NoteItemBinding
import com.najed.notesapp.db.Note
import com.najed.notesapp.db.NotesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adapter(private val activity: MainActivity): RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    private var notesList = listOf<Note>()

    class ItemViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            noteTv.text = notesList[position].content

            editBtn.setOnClickListener {
                activity.alert(notesList[position])
            }

            deleteBtn.setOnClickListener {
                activity.viewModel.deleteNote(notesList[position])
            } // mi
        } // re
    } // do

    override fun getItemCount() = notesList.size

    fun update(notes: List<Note>){
        notesList = notes
        notifyDataSetChanged()
    }

}