package com.example.listit.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listit.R
import com.example.listit.data.Note
import com.example.listit.utils.NoteDiffUtil
import com.example.listit.utils.RandomColor
import kotlin.random.Random


class NoteRVAdapter(private val context: Context, private val listener: INoteRVAdapter): RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder>() {

    private var allNotes = emptyList<Note>()

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val noteDes: TextView = itemView.findViewById(R.id.tvNoteDes)
        val deleteButton: ImageView = itemView.findViewById(R.id.btnDelete)
        val noteCard: CardView = itemView.findViewById(R.id.cvNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
        //implementing delete button click
        viewHolder.deleteButton.setOnClickListener {
            //gets the position of the item clicked
            listener.onDeleteClicked(allNotes[viewHolder.adapterPosition])
        }
        //implementing note card click
        viewHolder.noteCard.setOnClickListener {
            listener.onCardClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.noteTitle.text = currentNote.title
        holder.noteDes.text = currentNote.des
//        val num = (0..10).random()
        val color = Color.argb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
        holder.noteCard.setCardBackgroundColor(color)
    }

    //this function is to update the recycler by diffUtil(only changing the changes made observed through viewModel, and not the whole list again)
    fun updateList(newList: List<Note>) {
        val diffUtil = NoteDiffUtil(allNotes, newList)
        //Calculates the list of update operations that can covert one list into the other one
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        //first replacing oldList with the new Updated List for next Updates
        this.allNotes = newList
        //updating the list
        diffResult.dispatchUpdatesTo(this)
    }
}

    //handling clicks
    interface INoteRVAdapter {
        fun onDeleteClicked(note: Note)
        fun onCardClicked(note: Note)
    }