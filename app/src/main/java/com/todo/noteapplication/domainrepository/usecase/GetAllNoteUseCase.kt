package com.todo.noteapplication.domainrepository.usecase

import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.domainrepository.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    operator fun invoke(): Flow<List<NoteData>> = noteRepository.getAllNote()
}