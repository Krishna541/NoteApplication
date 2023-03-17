package com.todo.noteapplication.domainrepository.repository

import com.todo.noteapplication.data.model.NoteData
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getAllNote(): Flow<List<NoteData>>
    fun searchNote(query: String): Flow<List<NoteData>>
    suspend fun insertNote(noteData: NoteData)
    suspend fun updateNote(noteData: NoteData)
    suspend fun deleteNote(noteData: NoteData)
    suspend fun deleteAllNote()
}