package com.najed.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.najed.notesapp.Models.Note

class SingleNoteFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }
    lateinit var contentEditText: EditText
    lateinit var updateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_single_note, container, false)
        contentEditText = view.findViewById(R.id.content_et)
        updateButton = view.findViewById(R.id.update_btn)

        setFragmentResultListener("noteInfo") { _, bundle ->
            val note = Note(bundle.getString("noteID")!!, bundle.getString("noteContent")!!)
            contentEditText.setText(note.content)
            updateButton.setOnClickListener {
                if (contentEditText.text.isNotEmpty()){
                    viewModel.updateNote(note, Note(note.id, contentEditText.text.toString()))
                    Navigation.findNavController(view)
                        .navigate(R.id.action_singleNoteFragment_to_allNotesFragment)
                }
                else
                    Toast.makeText(view.context, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}