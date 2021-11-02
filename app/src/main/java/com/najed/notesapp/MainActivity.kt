package com.najed.notesapp

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.db.Note

class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesAdapter: Adapter
    private lateinit var messageEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.getAllNotes().observe(this, {
            notesList -> notesAdapter.update(notesList)
        })

        notesRecyclerView = findViewById(R.id.notes_rv)
        notesAdapter = Adapter(this)
        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        messageEditText = findViewById(R.id.message_et)
        submitButton = findViewById(R.id.submit_btn)
        submitButton.setOnClickListener {
            if (messageEditText.text.toString().isNotEmpty()){
                viewModel.addNote(Note(0, messageEditText.text.toString()))
                messageEditText.setText("")
            }
            else {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun alert(currentNote: Note) { // no, alert is not fun
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.alert_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.new_text_et)
        editText.setText(currentNote.content)
        dialogBuilder.setView(dialogLayout)
        dialogBuilder.setPositiveButton("save") { _, _ ->
            viewModel.updateNote(currentNote, Note(currentNote.id, editText.text.toString()))
        }
        dialogBuilder.setNegativeButton("cancel") { dialog, _ ->
            dialog.cancel()
        }

        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.show()
    }
}