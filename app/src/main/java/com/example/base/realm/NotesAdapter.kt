package com.example.base.realm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.base.R
import kotlinx.android.synthetic.main.list_note.view.*

class NotesAdapter(private val clickListener:OnItemClickListener) :RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){
    var notes = listOf<Notes>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_note,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notes[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditClick(notes[position])
        }
    }

    override fun getItemCount() = notes.size

    inner class NotesViewHolder(private val v:View):RecyclerView.ViewHolder(v){
        fun bind(note: Notes){
            v.titleTV.text = note.title
            v.descriptionTV.text = note.description
        }
    }
    interface OnItemClickListener{
        fun onItemEditClick(notes: Notes?)
    }
}