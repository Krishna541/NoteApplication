package com.todo.noteapplication.dihilt

import android.content.Context
import androidx.room.Room
import com.todo.noteapplication.controller.Constants.NOTE_DB_NAME
import com.todo.noteapplication.data.repository.NoteRepository
import com.todo.noteapplication.data.local.dao.NoteDao
import com.todo.noteapplication.data.local.db.NoteDatabase
import com.todo.noteapplication.domainrepository.repository.INoteRepository
import com.todo.noteapplication.domainrepository.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteAppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        NOTE_DB_NAME
    ).build()

    @Singleton
    @Provides
    fun providesNoteDao(database: NoteDatabase) = database.noteDao()

    @Singleton
    @Provides
    fun providesRepository(noteDao: NoteDao): INoteRepository = NoteRepository(noteDao)

    @Singleton
    @Provides
    fun providesUseCase(repository: INoteRepository): UseCaseModel {
        return UseCaseModel(
            getAllNotes = GetAllNoteUseCase(repository),
            insertNote = InsertNoteUseCase(repository),
            searchNote = SearchNoteUseCase(repository),
            deleteAllNote = DeleteAllNoteUseCase(repository),
            deleteNote = DeleteNoteUseCase(repository),
            updateNote = UpdateNoteUseCase(repository)
        )
    }

}