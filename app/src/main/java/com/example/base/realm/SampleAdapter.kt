package com.example.base.realm

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.base.R
import kotlinx.android.synthetic.main.list_note.view.*

class SampleAdapter :RecyclerView.Adapter<SampleAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notes>() {

        override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_note,parent,false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.itemView.apply {
            titleTV.text = note.title
            descriptionTV.text = note.description
            setOnClickListener{
                onItemClickListener?.let { it(note) }
            }
        }
    }

    private var onItemClickListener:((Notes) -> Unit)? = null

    fun setOnClickListener(listener:(Notes) -> Unit){
        onItemClickListener = listener
    }
}