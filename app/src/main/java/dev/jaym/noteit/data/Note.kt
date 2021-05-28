package dev.jaym.noteit.data

import androidx.room.ColumnInfo
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

    @ColumnInfo(name = "noteTitle")
    val title: String?,

    @ColumnInfo(name = "noteDes")
    val des: String?,

    @ColumnInfo(name = "noteColor")
    var color: Int?

    ): Serializable {


}


