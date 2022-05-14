package com.example.noteup.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteup.model.Note
import com.example.noteup.util.DateConverter
import com.example.noteup.util.UUIDConverter

@Database(entities = [Note::class],
        version = 1,
        exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}