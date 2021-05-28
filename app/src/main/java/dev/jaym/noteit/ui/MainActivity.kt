package dev.jaym.noteit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.*
import dev.jaym.noteit.adapter.INoteRVAdapter
import dev.jaym.noteit.adapter.NoteRVAdapter
import dev.jaym.noteit.data.Note
import com.google.android.material.snackbar.Snackbar
import dev.jaym.noteit.R
import dev.jaym.noteit.databinding.ActivityMainBinding
import dev.jaym.noteit.extensions.startAnimation

class MainActivity : AppCompatActivity(), INoteRVAdapter, androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NoteRVAdapter

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


        //initializing recyclerView
        binding?.recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        //initializing adapter
        adapter = NoteRVAdapter(this, this)

        //passing adapter to recyclerView
        binding?.recyclerView?.adapter = adapter

        //creating a instance or object of viewModel
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list -> list?.let {
            //checking if the list is null that is no change is observed then this function won't get executed
            adapter.updateList(it)
        }
        })


        //implementing swipe to delete
        val itemTouchHelper = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                //this will be empty as on up and down movement we do not need to do any thing with note
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //getting the position of the item swiped
                val position = viewHolder.adapterPosition
                //now getting the note from database using position in RV
                val note = adapter.getNote(position)
                //deleting the note using viewModel function
                viewModel.deleteNote(note)
                //making a snackbar to show that note is deleted and giving an option to undo the delete
                Snackbar.make(binding?.root!!, "Note Deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        //saving back the note in database that was deleted
                        viewModel.insertNote(note)
                    }
                    show()
                }
            }
        }

        //making the itemTouchHelper and attaching to recyclerView
        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(binding?.recyclerView)
        }

    }

    override fun onResume() {
        super.onResume()
        binding?.root?.isVisible = true
    }

//    override fun onDeleteClicked(note: Note) {
//        //whenever delete on a note is clicked we call the delete fun in viewModel
//        viewModel.deleteNote(note)
//
//    }

    override fun onCardClicked(note: Note) {
        //whenever a card of note is clicked we navigate to openNote activity passing the note
        val openNoteIntent = Intent(this, OpenNote::class.java)
        openNoteIntent.putExtra("SelectedNote", note)
        startActivity(openNoteIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    //making a fun to search database using query entered and updating the recyclerview with the result
    private fun searchDatabase(searchQuery: String) {
        val searchQuery = "%$searchQuery%" //% is added to format the sql query for searching

        viewModel.searchNote(searchQuery).observe(this) {
            it.let {
                adapter.updateList(it)
            }
        }
    }
}
