package com.example.base.realm

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.base.R
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_add_notes.*

class AddNotesFragment:Fragment(R.layout.fragment_add_notes){

    private lateinit var viewModel: NotesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, NotesViewModelFactory()).get(NotesViewModel::class.java)
        saveNotesButton.setOnClickListener{
            if (titleEditText.text.isNullOrEmpty() && descEditText.text.isNullOrEmpty())
                Toast.makeText(activity, "Please Enter the Title and Description", Toast.LENGTH_SHORT).show()
            else if(titleEditText.text.isNullOrEmpty())
                Toast.makeText(activity, "Please Enter the Title", Toast.LENGTH_SHORT).show()
            else if(descEditText.text.isNullOrEmpty())
                Toast.makeText(activity, "Please Enter the Description", Toast.LENGTH_SHORT).show()
            else{
                viewModel.addNote(
                    titleEditText.text.toString(),
                    descEditText.text.toString()
                )
                Snackbar.make(view,"Note Added Successfully" , Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
            view.hideKeyboard()
        }
    }
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

