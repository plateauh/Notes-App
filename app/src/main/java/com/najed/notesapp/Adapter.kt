package com.najed.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.databinding.NoteItemBinding
import com.najed.notesapp.Models.Note

class Adapter(private val fragment: AllNotesFragment): RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    private var notesList = listOf<Note>()

    class ItemViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notesList[position]
        holder.binding.apply {
            noteTv.text = note.content

            editBtn.setOnClickListener {
                fragment.navigate(note)
            }

            deleteBtn.setOnClickListener {
                fragment.viewModel.deleteNote(notesList[position])
            } // mi
        } // re
    } // do

    override fun getItemCount() = notesList.size

    fun update(notes: List<Note>){
        notesList = notes
        notifyDataSetChanged()
    }

}