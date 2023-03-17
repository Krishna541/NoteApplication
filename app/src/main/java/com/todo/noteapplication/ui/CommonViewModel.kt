package com.todo.noteapplication.ui

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.todo.noteapplication.data.model.NoteData

class CommonViewModel(
    application: Application,
) : AndroidViewModel(application) {

    val isDatabaseEmpty: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkValidation(
        title: String,
        description: String,
    ): Boolean = !(title.isEmpty() || description.isEmpty())

    fun checkIfDataEmpty(noteData: List<NoteData>) {
        isDatabaseEmpty.value = noteData.isEmpty()
    }

    fun showToast(context : Context, message :String){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }



}