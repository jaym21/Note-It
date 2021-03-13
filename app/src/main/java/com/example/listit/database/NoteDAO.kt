package com.example.listit.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.listit.data.Note

//making DAO(Data Access Object) to access the data in entity(table) and perform queries or operation on database
@Dao
interface NoteDAO {

    //making abstract functions
    //suspend makes the function background function which does not put load on the app(can only be called from a background thread)
    @Insert(onConflict = OnConflictStrategy.IGNORE) //if same type of note is being added it will be ignored by help of onConflict
    suspend fun  insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * from notes_table order by id ASC") //getting all notes by order of their id
    fun getAllNotes(): LiveData<List<Note>> //LiveData is used to observe the changes made to data
}