package dev.jaym.noteit.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.data.NoteRepository
import dev.jaym.noteit.database.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Using LiveData and caching what allNotes returns has several benefits:
// - We can put an observer on the data (instead of polling for changes) and only update the the UI when the data actually changes.
// - Repository is completely separated from the UI through the ViewModel.

//android viewModel takes an parameter application which passed through constructor
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    //getting notes which is LiveData
    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    //to get allnotes from database through dao we need to create a instance of database
    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        //instantiating repository which will communicate with dao to get allnotes
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    //calling insert function in repository, as it is a suspend fun, so we need to call it through viewModelScope which makes a background thread or coroutine
    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    //calling delete function in repository, as it is a suspend fun, so we need to call it through viewModelScope which makes a background thread or coroutine
    fun removeNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    //calling update function in repository
    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
}