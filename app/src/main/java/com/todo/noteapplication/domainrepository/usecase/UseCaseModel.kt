package com.todo.noteapplication.domainrepository.usecase

data class UseCaseModel(
    val deleteNote: DeleteNoteUseCase,
    val deleteAllNote: DeleteAllNoteUseCase,
    val getAllNotes: GetAllNoteUseCase,
    val insertNote: InsertNoteUseCase,
    val searchNote: SearchNoteUseCase,
    val updateNote: UpdateNoteUseCase,
)
