package com.todo.noteapplication.ui.updateNote

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.todo.noteapplication.R
import com.todo.noteapplication.ui.NoteViewModel
import com.todo.noteapplication.ui.CommonViewModel
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.databinding.FragmentUpdateNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update_note) {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val noteVm: NoteViewModel by viewModels()
    private val commonvm: CommonViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateNoteBinding.bind(view)

        binding.etUpdateNoteTitle.setText(args.noteParcel.title)
        binding.etUpdateNoteDesc.setText(args.noteParcel.description)

        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_update, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_update_save -> updateNote()
                    R.id.menu_update_delete -> deleteNote()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateNote() {
        val title = binding.etUpdateNoteTitle.text.toString()
        val descNote = binding.etUpdateNoteDesc.text.toString()

        val validation = commonvm.checkValidation(title, descNote)
        if (validation) {
            val updateNoteData = NoteData(
                args.noteParcel.id,
                title,
                descNote
            )
            noteVm.updateData(updateNoteData)
            commonvm.showToast(requireContext(),"\"Note Updated Successfully.\"")
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            commonvm.showToast(requireContext(),"Please add required detail.")
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.noteParcel.title}?'")
        builder.setMessage("Are you sure you want to delete '${args.noteParcel.title}?'")
        builder.setPositiveButton("Yes") { _, _ ->
            noteVm.deleteData(args.noteParcel)
            commonvm.showToast(requireContext(),"${args.noteParcel.title + " "} Deleted Successfully.")

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}