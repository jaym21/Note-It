package com.example.listit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_note.view.*

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        //initializing adapter
        val adapter = NoteRVAdapter(this, this)
        //passing adapter to recyclerView
        recyclerView.adapter = adapter

        //creating a instance or object of viewmodel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list -> list?.let {
            //checking if the list is null that is no change is observed then this function won't get executed
            adapter.updateList(it)
        }
        })
    }

    override fun onItemClicked(note: Note) {
        //whenever a note is clicked we call the delete fun in viewModel
        viewModel.deleteNote(note)
    }

    fun addData(view: View) {
        //getting the text to be added to the note list
        val noteText = input.text.toString()
        //checking text is empty or not before adding to the list
        if (noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
        }
    }
}
