package com.najed.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.databinding.NoteItemBinding

class Adapter(private val notesList: ArrayList<String>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            noteTv.text = notesList[position]
        }
    }

    override fun getItemCount() = notesList.size
}