package dev.jaym.noteit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dev.jaym.noteit.R
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.databinding.ActivityAddNoteBinding

class AddNote : AppCompatActivity() {

    private var binding: ActivityAddNoteBinding? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        //back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }

        binding?.btnAdd?.setOnClickListener {
            if (binding?.etNoteTitle?.text.toString().isNotEmpty() && binding?.etNoteDes?.text.toString().isNotEmpty()) {

                //getting a random color for background
                val colorsArray = resources.getIntArray(R.array.cardColors)
                val randomInt = (0..9).random()
                Log.d("TAGYOYO", "RANDOM COLOR $randomInt")
                val randomColor =  colorsArray[randomInt]
                Log.d("TAGYOYO", "RANDOM COLOR $randomColor")
                viewModel.addNote(Note(0, binding?.etNoteTitle?.text.toString(), binding?.etNoteDes?.text.toString(), randomColor))
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }else {
                Snackbar.make(binding?.root!!, "Add title and description of the note to be added", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}