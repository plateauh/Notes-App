package com.najed.notesapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Alert (private val currentNote: Note,
             context: Context) {
    init {
        val dialogBuilder = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogLayout = layoutInflater.inflate(R.layout.alert_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.new_text_et)
        editText.setText(currentNote.content)
        dialogBuilder.setView(dialogLayout)
        dialogBuilder.setPositiveButton("save") { _, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                NotesDatabase.getInstance(context).NoteDao().updateNote(currentNote, Note(currentNote.id, editText.text.toString())) // note that I passed the context instead of the application context
            }
        }
        dialogBuilder.setNegativeButton("cancel") { dialog, _ ->
             dialog.cancel()
         }

        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.show()
    }
}