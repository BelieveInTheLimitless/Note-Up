package com.example.noteup.repository

import com.example.noteup.data.NoteDatabaseDao
import com.example.noteup.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDatabaseDao) {

    suspend fun addNote(note: Note) = noteDatabaseDao.insert(note)

    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)

    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao
        .getNotes()
        .flowOn(Dispatchers.IO)
        .conflate()
}