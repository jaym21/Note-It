package com.example.listit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listit.data.Note
import com.example.listit.databinding.ActivityOpenNoteBinding

class OpenNote : AppCompatActivity() {

    private var binding: ActivityOpenNoteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }

        binding?.ivEditButton?.setOnClickListener {

        }

        val selectedNote: Note = intent.getSerializableExtra("SelectedNote") as Note

        binding?.tvTitleOpen?.text = selectedNote.title
        binding?.tvDesOpen?.text = selectedNote.des
    }
}