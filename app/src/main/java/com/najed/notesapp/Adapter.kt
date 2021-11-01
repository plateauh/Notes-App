package com.najed.notesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.databinding.NoteItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adapter(private val context: Context,
              private val activity: MainActivity,
              private var notesList: List<Note>): RecyclerView.Adapter<Adapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            noteTv.text = notesList[position].content

            if (position % 2 == 0)
                noteLl.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
            else
                noteLl.setBackgroundColor(ContextCompat.getColor(context, R.color.white))

            editBtn.setOnClickListener {
                activity.alert(notesList[position])
            }

            deleteBtn.setOnClickListener {
                CoroutineScope(IO).launch {
                    NotesDatabase.getInstance(context).NoteDao().deleteNote(notesList[position])
                    notesList = NotesDatabase.getInstance(context).NoteDao().getAllNotes()
                    withContext(Main) {
                        update()
                    }
                }
            }
        }
    }

    override fun getItemCount() = notesList.size

    private fun update() {
        notifyDataSetChanged()
    }
}