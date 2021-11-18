package dev.jaym.noteit.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//making a table(entity) for database storage
@Entity(tableName = "notes_table")
//defining the schema of the table text as string
data class Note(
    //making an id for each text autoGenerate will automatically generate id for each text we don't need to pass
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String?,
    val des: String?,
    var color: Int?
): Serializable

