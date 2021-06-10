package dev.jaym.noteit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.databinding.ActivityEditNoteBinding

class EditNote : AppCompatActivity() {

    private var binding: ActivityEditNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val selectedNote: Note = intent.getSerializableExtra("EditSelectedNote") as Note

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }

        binding?.apply {
            etEditNoteTitle.setText(selectedNote.title ?: "")
            etEditNoteDes.setText(selectedNote.des ?: "")
            llEditBg.setBackgroundColor(selectedNote.color!!)
        }


        binding?.apply {
            btnSave.setOnClickListener {
                val editedNote = Note(
                    selectedNote.id,
                    etEditNoteTitle.text.toString().takeIf { it.isNotBlank() },
                    etEditNoteDes.text.toString().takeIf { it.isNotBlank() },
                    selectedNote.color
                )
                viewModel.updateNote(editedNote)
                Toast.makeText(this@EditNote, "Updated the note", Toast.LENGTH_SHORT).show()

                val mainIntent = Intent(this@EditNote, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}