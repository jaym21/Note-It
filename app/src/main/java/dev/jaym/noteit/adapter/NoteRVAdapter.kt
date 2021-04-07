package dev.jaym.noteit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.jaym.noteit.R
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.utils.NoteDiffUtil


class NoteRVAdapter(private val context: Context, private val listener: INoteRVAdapter): RecyclerView.Adapter<NoteRVAdapter.NoteViewHolder>() {

    private var allNotes = emptyList<Note>()
    private var num = 0

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val noteDes: TextView = itemView.findViewById(R.id.tvNoteDes)
//        val deleteButton: ImageView = itemView.findViewById(R.id.btnDelete)
        val noteCard: CardView = itemView.findViewById(R.id.cvNote)
        val llNote: LinearLayout = itemView.findViewById(R.id.llNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewHolder = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
        //implementing delete button click
//        viewHolder.deleteButton.setOnClickListener {
//            //gets the position of the item clicked
//            listener.onDeleteClicked(allNotes[viewHolder.adapterPosition])
//        }
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
        val colorsArray = context.resources.getIntArray(R.array.cardColors) as IntArray
        if (num >= 10) {
            num = 0
        }
        val randomColor = colorsArray[num]
        num++
        holder.llNote.setBackgroundColor(randomColor)
        currentNote.color = randomColor
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

    fun getNote(position: Int): Note {
        return allNotes[position]
    }
}

    //handling clicks
    interface INoteRVAdapter {
//        fun onDeleteClicked(note: Note)
        fun onCardClicked(note: Note)
    }