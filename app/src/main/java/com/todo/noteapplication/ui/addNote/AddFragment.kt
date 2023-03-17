package com.todo.noteapplication.ui.addNote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.todo.noteapplication.R
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.ui.NoteViewModel
import com.todo.noteapplication.ui.CommonViewModel
import com.todo.noteapplication.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add_note) {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private val noteVm: NoteViewModel by viewModels()
    private val commonvm: CommonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNoteBinding.bind(view)

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_add, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.add_item) {
                    insertDataToDatabase()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun insertDataToDatabase() {
        val noteTitle = binding.etAddNoteTitle.text.toString()
        val noteDesc = binding.etAddNoteMultiLine.text.toString()

        val validation = commonvm.checkValidation(noteTitle, noteDesc)

        if (validation) {
            val newData = NoteData(
                0,
                noteTitle,
                noteDesc
            )
            noteVm.insertData(newData)
            commonvm.showToast(requireContext(),"Note Created Successfully.")

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            commonvm.showToast(requireContext(),"Please add required detail.")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}