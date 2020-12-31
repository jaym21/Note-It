package com.example.listit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//making a table(entity) for database storage
@Entity(tableName = "notes_table")

//defining the schema of the table text as string
class Note(@ColumnInfo(name = "text")val text: String) {
    //making an id for each text autoGenerate will automatically generate id for each text we don't need to pass
    @PrimaryKey(autoGenerate = true) var id = 0
}

