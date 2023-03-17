package com.todo.noteapplication.controller

import android.view.View
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.todo.noteapplication.R
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.ui.noteList.ListFragmentDirections

object BindingAdapter {

    @BindingAdapter("android:redirectToAddFragment")
    @JvmStatic
    fun redirectToAddFragment(view: FloatingActionButton, navigate: Boolean) {
        view.setOnClickListener {
            if (navigate) view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        when (emptyDatabase.value) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.INVISIBLE
            else -> {  }
        }
    }

    @BindingAdapter("android:sendDataToUpdateFragment")
    @JvmStatic
    fun sendDataToUpdateFragment(view: CardView, noteData: NoteData) {
        view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(noteData)
            view.findNavController().navigate(action)
        }
    }

}