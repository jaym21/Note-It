package com.example.listit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listit.R
import com.example.listit.adapter.INoteRVAdapter
import com.example.listit.adapter.NoteRVAdapter
import com.example.listit.data.Note
import com.example.listit.databinding.ActivityMainBinding
import com.example.listit.extensions.startAnimation

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: NoteViewModel
    lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //making fab button animation
        val animation  = AnimationUtils.loadAnimation(this, R.anim.fab_explosion_anim).apply {
            duration = 700
            interpolator = AccelerateDecelerateInterpolator()
        }

        binding?.apply {
            fabAddNote.setOnClickListener {
                //first making fab button invisible
                fabAddNote.isVisible = false
                //making the circle view of animation visible
                circleAnim.isVisible = true
                //starting animation through extension function made
                circleAnim.startAnimation(animation) {
                    root.isVisible = false
                    circleAnim.isVisible = false
                    fabAddNote.isVisible = true
                    //opening addNote activity on click on fab button
                    val addNoteIntent = Intent(this@MainActivity, AddNote::class.java)
                    startActivity(addNoteIntent)
                }
            }
        }

        //making gridLayout
        gridLayoutManager = GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)

        //initializing recyclerView
        binding?.recyclerView?.layoutManager = gridLayoutManager

        //initializing adapter
        val adapter = NoteRVAdapter(this, this)

        //passing adapter to recyclerView
        binding?.recyclerView?.adapter = adapter

        //creating a instance or object of viewModel
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list -> list?.let {
            //checking if the list is null that is no change is observed then this function won't get executed
            adapter.updateList(it)
        }
        })

    }

    override fun onResume() {
        super.onResume()
        binding?.root?.isVisible = true
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
