package com.example.base.realm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.R
import kotlinx.android.synthetic.main.realm_main.*

class RealmMain:Fragment(R.layout.realm_main),NotesAdapter.OnItemClickListener {
    private lateinit var viewModel:NotesViewModel
    private lateinit var notesAdapter:NotesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, NotesViewModelFactory()).get(NotesViewModel::class.java)
        addNotesButton.setOnClickListener{
            val act = RealmMainDirections.actionRealmMainToAddNotesFragment()
            findNavController().navigate(act)
        }
        initialiseRecyclerView()
        getNotes()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun createUpdateDialog(notes: Notes){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog,null, false)
        val builder = activity?.let { AlertDialog.Builder(it) }
        val titleEdtxt: EditText = dialogView.findViewById(R.id.titleEditText_update)
        val descriptionEdtxt: EditText = dialogView.findViewById(R.id.descriptionEditText_update)
        titleEdtxt.setText(notes.title)
        descriptionEdtxt.setText(notes.description)
        builder?.setView(dialogView)
        builder?.setTitle("Update Note")
        builder?.setPositiveButton("Update") { _, _ ->
            viewModel.updateNote(
                notes.id!!,
                titleEdtxt.text.toString(),
                descriptionEdtxt.text.toString()
            )
            notesAdapter.notifyDataSetChanged()
            Toast.makeText(activity,"Updated Note",Toast.LENGTH_SHORT).show()
        }
        builder?.setNegativeButton("Cancel") { _, _ ->}
        builder?.show()
    }
    private fun initialiseRecyclerView(){
        notesAdapter = NotesAdapter(this)
        notesRV.adapter = notesAdapter
        notesRV.layoutManager = LinearLayoutManager(activity)
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun getNotes(){
        viewModel.allNotes.observe(viewLifecycleOwner, Observer{
            notesAdapter.notes = it
            notesAdapter.notifyDataSetChanged()
        })
    }
    override fun onItemEditClick(notes: Notes?) {
        createUpdateDialog(notes!!)
    }
}

