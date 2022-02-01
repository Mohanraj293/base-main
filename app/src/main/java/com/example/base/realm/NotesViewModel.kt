package com.example.base.realm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.Realm
import java.util.*

class NotesViewModel:ViewModel(){
    private val realm = Realm.getDefaultInstance()

    val allNotes: LiveData<List<Notes>> = getNotesList()


    private fun getNotesList():MutableLiveData<List<Notes>>{
        val list = MutableLiveData<List<Notes>>()
        val notes = realm.where(Notes::class.java).findAll()
        list.value = notes
        return list
    }

    fun addNote(noteTitle:String,noteDescription:String){
//        val notes = Notes()
//        val currentId = realm.where(Notes::class.java).max("id")
//        val nextId = if (currentId == null) 1 else currentId.toInt() + 1
//        notes.id = nextId
//        notes.title = noteTitle
//        notes.description = noteDescription
//        realm.executeTransaction{
//            it.insert(notes)
//        }
        realm.executeTransaction { r: Realm ->
            val note = r.createObject(Notes::class.java, UUID.randomUUID().toString())
            note.title = noteTitle
            note.description = noteDescription
            realm.insertOrUpdate(note)
        }
    }
    fun updateNote(id: String, noteTitle: String, noteDesc: String) {
        val target = realm.where(Notes::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            target?.title = noteTitle
            target?.description = noteDesc
            realm.insertOrUpdate(target)
        }
    }

    fun deleteNote(id: String){
        val notes = realm.where(Notes::class.java)
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            notes?.deleteFromRealm()
        }
    }
}