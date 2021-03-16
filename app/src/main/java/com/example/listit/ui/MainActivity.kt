package com.example.listit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listit.adapter.INoteRVAdapter
import com.example.listit.adapter.NoteRVAdapter
import com.example.listit.data.Note
import com.example.listit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: NoteViewModel
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        //making gridLayout
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)

        //initializing recyclerView
        binding?.recyclerView?.layoutManager = gridLayoutManager

        //initializing adapter
        val adapter = NoteRVAdapter(this, this)

        //passing adapter to recyclerView
        binding?.recyclerView?.adapter = adapter

        //creating a instance or object of viewmodel
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list -> list?.let {
            //checking if the list is null that is no change is observed then this function won't get executed
            adapter.updateList(it)
        }
        })

    }


    override fun onDeleteClicked(note: Note) {
        //whenever delete on a note is clicked we call the delete fun in viewModel
        viewModel.deleteNote(note)

    }

    override fun onCardClicked(note: Note) {
        //whenever a card of note is clicked we navigate to openNote activity passing the note
        val openNoteIntent = Intent(this, OpenNote::class.java)
        openNoteIntent.putExtra("SelectedNote", note)
        startActivity(openNoteIntent)
    }

    fun openAddNote(view: View) {
        //opening addNote activity on click on fab button
        val addNoteIntent = Intent(this, AddNote::class.java)
        startActivity(addNoteIntent)
    }
}
