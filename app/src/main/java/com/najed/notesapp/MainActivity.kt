package com.najed.notesapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var notesList: List<Note>
    lateinit var notesRecyclerView: RecyclerView
    lateinit var messageEditText: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRecyclerView = findViewById(R.id.notes_rv)
        notesRecyclerView.layoutManager = LinearLayoutManager(this)
        CoroutineScope(IO).launch {
            notesList = NotesDatabase.getInstance(applicationContext).NoteDao().getAllNotes()
            withContext(Main){
                notesRecyclerView.adapter = Adapter(this@MainActivity, this@MainActivity, notesList)
            }
        }

        messageEditText = findViewById(R.id.message_et)
        submitButton = findViewById(R.id.submit_btn)
        submitButton.setOnClickListener {
            if (messageEditText.text.toString().isNotEmpty()){
                CoroutineScope(IO).launch {
                    NotesDatabase.getInstance(applicationContext).NoteDao().addNote(Note(0, messageEditText.text.toString()))
                    notesList = NotesDatabase.getInstance(this@MainActivity).NoteDao().getAllNotes()
                    withContext(Main){
                        notesRecyclerView.adapter = Adapter(this@MainActivity, this@MainActivity, notesList)
                    }
                }
                messageEditText.setText("")
            }
            else {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun alert(currentNote: Note) { // no, alert is not fun
        val dialogBuilder = AlertDialog.Builder(this)
        val layoutInflater = LayoutInflater.from(this)
        val dialogLayout = layoutInflater.inflate(R.layout.alert_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.new_text_et)
        editText.setText(currentNote.content)
        dialogBuilder.setView(dialogLayout)
        dialogBuilder.setPositiveButton("save") { _, _ ->
            CoroutineScope(IO).launch {
                NotesDatabase.getInstance(this@MainActivity).NoteDao().updateNote(currentNote, Note(currentNote.id, editText.text.toString()))
                notesList = NotesDatabase.getInstance(this@MainActivity).NoteDao().getAllNotes()
                withContext(Main) {
                    notesRecyclerView.adapter = Adapter(this@MainActivity, this@MainActivity, notesList)
                }
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