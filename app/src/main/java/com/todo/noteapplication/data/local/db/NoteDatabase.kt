package com.todo.noteapplication.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.data.local.dao.NoteDao

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}