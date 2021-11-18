package dev.jaym.noteit.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar
import dev.jaym.noteit.adapter.INoteRVAdapter
import dev.jaym.noteit.adapter.NoteRVAdapter
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.R
import dev.jaym.noteit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), INoteRVAdapter {

    private var binding: ActivityMainBinding? = null
    lateinit var viewModel: NoteViewModel
    lateinit var noteAdapter: NoteRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.fabAddNote?.setOnClickListener {
            //opening addNote activity on click on fab button
            val addNoteIntent = Intent(this@MainActivity, AddNote::class.java)
            startActivity(addNoteIntent)
        }

        //initializing recyclerView
        binding?.recyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        //initializing adapter
        noteAdapter = NoteRVAdapter(this, this)

        //passing adapter to recyclerView
        binding?.recyclerView?.adapter = noteAdapter

        //setting swipe to delete item
        setUpSwipeToDeleteItem()

        //creating a instance or object of viewModel
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {
            Log.d("TAGYOYO", "$it")
            noteAdapter.submitList(it)
        })
    }

    private fun setUpSwipeToDeleteItem() {
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                //this will be empty as on up and down movement we do not need to do any thing with note
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //getting position of item which is swiped
                val itemPosition = viewHolder.adapterPosition
                //getting currentList
                val currentList =  noteAdapter.currentList.toMutableList()
                //getting the swiped item
                val swipedItem = currentList[itemPosition]
                //removing item from list
                currentList.removeAt(itemPosition)

                //removing from database
                viewModel.removeNote(swipedItem)

                //updating recycler view
                noteAdapter.submitList(currentList)

                val snackbar = Snackbar.make(binding?.root!!, "Note removed", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") {
                    val newCurrentList  = noteAdapter.currentList.toMutableList()
                    newCurrentList.add(itemPosition, swipedItem)

                    //adding item back to database
                    viewModel.addNote(swipedItem)
                    //updating recycler view
                    noteAdapter.submitList(newCurrentList)
                }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(binding?.recyclerView)
    }

    private fun runRecyclerViewAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recyclerview_animation)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }

    override fun onResume() {
        super.onResume()
        runRecyclerViewAnimation(binding?.recyclerView!!)
    }

    override fun onCardClicked(note: Note) {
        //whenever a card of note is clicked we navigate to openNote activity passing the note
        val openNoteIntent = Intent(this, OpenNote::class.java)
        openNoteIntent.putExtra("SelectedNote", note)
        startActivity(openNoteIntent)
    }
}
