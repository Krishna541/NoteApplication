package com.todo.noteapplication.domainrepository.usecase

import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.domainrepository.repository.INoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke(noteData: NoteData) = noteRepository.insertNote(noteData)
}