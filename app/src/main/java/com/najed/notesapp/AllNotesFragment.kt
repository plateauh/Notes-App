package com.najed.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.najed.notesapp.Models.Note

class AllNotesFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }
    lateinit var fragmentView: View
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesAdapter: Adapter
    private lateinit var messageEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_all_notes, container, false)
        viewModel.getNotes().observe(viewLifecycleOwner, {
                notesList -> notesAdapter.update(notesList)
        })
        notesRecyclerView = fragmentView.findViewById(R.id.notes_rv)
        notesAdapter = Adapter(this)
        notesRecyclerView.adapter = notesAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(fragmentView.context)

        messageEditText = fragmentView.findViewById(R.id.message_et)
        submitButton = fragmentView.findViewById(R.id.submit_btn)
        submitButton.setOnClickListener {
            if (messageEditText.text.toString().isNotEmpty()){
                viewModel.addNote(Note("", messageEditText.text.toString()))
                messageEditText.setText("")
            }
            else {
                Toast.makeText(fragmentView.context, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }
        return fragmentView
    }

    fun navigate(note: Note) {
        setFragmentResult("noteInfo", bundleOf("noteID" to note.id, "noteContent" to note.content))
        Navigation.findNavController(fragmentView)
            .navigate(R.id.action_allNotesFragment_to_singleNoteFragment)
    }

}