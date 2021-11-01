package com.najed.notesapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast

class Alert (private val currentNote: String,
             context: Context,
             private val applicationContext: Context) {
    init {
        val dialogBuilder = AlertDialog.Builder(context)
        val layoutInflater = LayoutInflater.from(context)
        val dialogLayout = layoutInflater.inflate(R.layout.alert_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.new_text_et)
        editText.setText(currentNote)
        dialogBuilder.setView(dialogLayout)
        dialogBuilder.setPositiveButton("save") { _, _ ->
            val isUpdated = DBHelper(applicationContext).updateNote(currentNote, editText.text.toString())
            if (isUpdated) Toast.makeText(context, "Note updated successfully", Toast.LENGTH_SHORT).show()
        }
        dialogBuilder.setNegativeButton("cancel") { dialog, _ ->
             dialog.cancel()
         }

        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.show()
    }
}