package com.todo.noteapplication.domainrepository.usecase

import com.todo.noteapplication.domainrepository.repository.INoteRepository
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    operator fun invoke(query: String) = noteRepository.searchNote(query)
}