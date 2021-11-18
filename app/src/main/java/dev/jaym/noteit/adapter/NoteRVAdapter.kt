package dev.jaym.noteit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jaym.noteit.R
import dev.jaym.noteit.data.Note


class NoteRVAdapter(private val context: Context, private val listener: INoteRVAdapter): ListAdapter<Note, NoteRVAdapter.NoteViewHolder>(NoteDiffUtil()) {

    class NoteDiffUtil: DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.tvNoteTitle)
        val noteDes: TextView = itemView.findViewById(R.id.tvNoteDes)
        val noteCard: CardView = itemView.findViewById(R.id.cvNote)
        val llNote: LinearLayout = itemView.findViewById(R.id.llNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
         return NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)
        holder.noteTitle.text = currentNote.title
        holder.noteDes.text = currentNote.des
        holder.llNote.setBackgroundColor(currentNote.color!!)
        holder.noteCard.setOnClickListener {
            listener.onCardClicked(getItem(position))
        }
    }

}
//handling clicks
interface INoteRVAdapter {
    fun onCardClicked(note: Note)
}