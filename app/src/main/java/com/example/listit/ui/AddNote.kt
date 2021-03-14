package com.example.listit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.listit.R
import com.example.listit.data.Note
import com.example.listit.databinding.ActivityAddNoteBinding
import com.example.listit.utils.RandomColor

class AddNote : AppCompatActivity() {

    private var binding: ActivityAddNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding?.btnAdd?.setOnClickListener {
            if (binding?.etNoteTitle?.text.toString().isNotEmpty() && binding?.etNoteDes?.text.toString().isNotEmpty()) {
                addNote(binding?.etNoteTitle?.text.toString(), binding?.etNoteDes?.text.toString())
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }else {
                Toast.makeText(this, "Add title and description of the note to be added", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun addNote (title: String, des: String) {
        //adding the note by calling insertNote fun in viewModel
        viewModel.insertNote(Note(title, des))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}