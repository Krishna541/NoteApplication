package com.todo.noteapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.domainrepository.usecase.UseCaseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCaseModel: UseCaseModel,
) : ViewModel() {

    private val _getAllData = MutableLiveData<List<NoteData>>()
    val getAllData: LiveData<List<NoteData>> = _getAllData

    private val _searchNote = MutableLiveData<List<NoteData>>()
    val searchNote: LiveData<List<NoteData>> = _searchNote

    fun getAllData() {
        viewModelScope.launch {
            useCaseModel.getAllNotes.invoke().collect {
                _getAllData.postValue(it)
            }
        }
    }

    fun searchNote(query: String) {
        viewModelScope.launch {
            useCaseModel.searchNote.invoke(query).collect {
                _searchNote.postValue(it)
            }
        }
    }

    fun insertData(noteData: NoteData) {
        viewModelScope.launch {
            useCaseModel.insertNote.invoke(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch {
            useCaseModel.updateNote.invoke(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch {
            useCaseModel.deleteNote.invoke(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            useCaseModel.deleteAllNote.invoke()
        }
    }

}