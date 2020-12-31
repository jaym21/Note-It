package com.example.listit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import javax.xml.transform.ErrorListener

class NoteRVAdapter(private val context: Context, private val listener: INoteRVAdapter): RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder>() {

    val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
        viewHolder.deleteButton.setOnClickListener {
            //gets the position of the item clicked
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.textView.text = currentNote.text
    }

    //this function is to tell recyclerview the changes observed by viewmodel
    fun updateList(newList: List<Note>) {
        //first clearing the older list of notes
        allNotes.clear()
        //adding the newList in which new note the changes are present
        allNotes.addAll(newList)

        //updating the recyclerview with changes
        notifyDataSetChanged()

    }
}

//handling clicks
interface INoteRVAdapter {
    fun onItemClicked(note: Note)
}