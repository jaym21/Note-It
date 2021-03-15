package com.example.listit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.listit.data.Note
import com.example.listit.databinding.ActivityEditNoteBinding

class EditNote : AppCompatActivity() {

    private var binding: ActivityEditNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        val selectedNote: Note = intent.getSerializableExtra("EditSelectedNote") as Note

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)


        binding?.apply {
            etNoteTitle.setText(selectedNote.title ?: "")
            etNoteDes.setText(selectedNote.des ?: "")
        }


        binding?.apply {
            btnSave.setOnClickListener {
                viewModel.updateNote(Note(etNoteTitle.text.toString().takeIf { it.isNotBlank() }!!,
                    etNoteDes.text.toString().takeIf { it.isNotBlank() }!!
                ))
                Toast.makeText(this@EditNote, "Updated the note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}