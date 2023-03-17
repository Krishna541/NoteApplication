package com.todo.noteapplication.data.repository

import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.data.local.dao.NoteDao
import com.todo.noteapplication.domainrepository.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) : INoteRepository {

    override fun getAllNote(): Flow<List<NoteData>> = noteDao.getAllData()
    override fun searchNote(query: String): Flow<List<NoteData>> = noteDao.searchDatabase(query)
    override suspend fun insertNote(noteData: NoteData) = noteDao.insertData(noteData)
    override suspend fun updateNote(noteData: NoteData) = noteDao.updateData(noteData)
    override suspend fun deleteNote(noteData: NoteData) = noteDao.deleteData(noteData)
    override suspend fun deleteAllNote() = noteDao.deleteAll()

}