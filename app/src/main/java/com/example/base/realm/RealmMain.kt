package com.example.base.realm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.R
import com.example.base.databinding.RealmMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.realm_main.*


class RealmMain:Fragment(R.layout.realm_main),NotesAdapter.Interaction{
    private lateinit var viewModel:NotesViewModel
    private lateinit var notesAdapter:NotesAdapter
   //View Binding
   //private lateinit var binding: RealmMainBinding

    //OnViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, NotesViewModelFactory()).get(NotesViewModel::class.java)
        addNotesButton.setOnClickListener{
            val act = RealmMainDirections.actionRealmMainToAddNotesFragment()
            findNavController().navigate(act)
        }
        initialiseRecyclerView()
        getNotes()
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.adapterPosition
                val note = notesAdapter.differ.currentList[pos]
                viewModel.deleteNote(note)
                notesAdapter.notifyItemRemoved(pos)
                Snackbar.make(view,"Note deleted Successfully" , Snackbar.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(notesRV)
    }

    //Update Note fun
    private fun createUpdateDialog(pos: Int, notes: Notes){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog,null, false)
        val builder = activity?.let { AlertDialog.Builder(it) }
        val titleEdtxt: EditText = dialogView.findViewById(R.id.titleEditText_update)
        val descriptionEdtxt: EditText = dialogView.findViewById(R.id.descriptionEditText_update)
        titleEdtxt.setText(notes.title)
        descriptionEdtxt.setText(notes.description)
        builder?.setView(dialogView)
        builder?.setCancelable(false)
        builder?.setTitle("Update Note")
        builder?.setPositiveButton("Update") { _, _ ->
            viewModel.updateNote(
                notes.id!!,
                titleEdtxt.text.toString(),
                descriptionEdtxt.text.toString()
            )
            notesAdapter.notifyItemChanged(pos)
            view?.let { Snackbar.make(it,"Note Updated Successfully" , Snackbar.LENGTH_SHORT).show() }
        }
        builder?.setNegativeButton("Cancel") { _, _ ->}
        builder?.show()
    }

    //RecyclerView Initialization
    private fun initialiseRecyclerView(){
        notesRV.apply {
            layoutManager = LinearLayoutManager(activity)
            notesAdapter = NotesAdapter(this@RealmMain)
            adapter = notesAdapter
        }
    }

    //getting the notes list
    private fun getNotes(){
        viewModel.allNotes.observe(viewLifecycleOwner, Observer{
            notesAdapter.differ.submitList(it)
        })
    }

    //OnClickListener on particular item to update the note
    override fun onItemSelected(position: Int, item: Notes) {
        createUpdateDialog(position,item)
    }
}

