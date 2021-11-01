package com.najed.notesapp

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.databinding.NoteItemBinding

class Adapter(private val context: Context,
              private val applicationContext: Context,
              private var notesList: ArrayList<String>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            noteTv.text = notesList[position]

            if (position % 2 == 0)
                noteLl.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
            else
                noteLl.setBackgroundColor(ContextCompat.getColor(context, R.color.white))

            editBtn.setOnClickListener {
                Alert(notesList[position], context, applicationContext)
                update()
            }

            deleteBtn.setOnClickListener {
                val isDeleted = DBHelper(applicationContext).deleteNote(notesList[position])
                if (isDeleted) Toast.makeText(context, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                update()
            }

        }
    }

    override fun getItemCount() = notesList.size

    fun update() {
        notesList = DBHelper(applicationContext).getAllNotes()
        notifyDataSetChanged()
    }
}