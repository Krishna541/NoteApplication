package com.todo.noteapplication.domainrepository.usecase

import com.todo.noteapplication.domainrepository.repository.INoteRepository
import javax.inject.Inject

class DeleteAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke() = noteRepository.deleteAllNote()
}