package com.todo.noteapplication.ui.noteList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.todo.noteapplication.controller.NoteDiffUtil
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.databinding.NoteItemBinding

class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.MyViewHolder>() {

    var noteData = listOf<NoteData>()

    class MyViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(noteData: NoteData) {
            binding.noteData = noteData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noteData[position])
    }

    override fun getItemCount(): Int = noteData.size

    fun setData(newData: List<NoteData>) {
        val noteDiffUtil = NoteDiffUtil(noteData, newData)
        val diffUtilResult = DiffUtil.calculateDiff(noteDiffUtil)
        noteData = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}