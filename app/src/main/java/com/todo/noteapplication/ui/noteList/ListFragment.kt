package com.todo.noteapplication.ui.noteList

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.todo.noteapplication.ui.NoteViewModel
import com.todo.noteapplication.ui.CommonViewModel
import com.todo.noteapplication.controller.observeOnce
import com.todo.noteapplication.data.model.NoteData
import com.todo.noteapplication.R
import com.todo.noteapplication.databinding.FragmentNoteListBinding
import com.todo.noteapplication.ui.noteList.adapter.NoteListAdapter
import com.todo.noteapplication.ui.noteList.listener.SwipeToDelete
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_note_list) {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val noteAdapter: NoteListAdapter by lazy { NoteListAdapter() }

    private val noteVm: NoteViewModel by viewModels()
    private val commonvm: CommonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteListBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedVm = commonvm

        setupMenu()
        setupRecycler()
        setupViewModel()
        searchItem()

    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_delete_all -> confirmDelete()
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun confirmDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete all Notes?")
        builder.setMessage("Are you sure you want to remove all notes?")
        builder.setPositiveButton("Yes") { _, _ ->
            noteVm.deleteAll()
            commonvm.showToast(requireContext(),"\"All Notes Deleted Successfully\"")
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun setupRecycler() {
        val recyclerView = binding.rvListFragment
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.setHasFixedSize(true)
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = noteAdapter.noteData[viewHolder.adapterPosition]

                noteVm.deleteData(deletedItem)
                noteAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeleteItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteItem(view: View, deletedItem: NoteData) {
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            noteVm.insertData(deletedItem)
        }
        snackBar.show()
    }

    private fun setupViewModel() {
        noteVm.getAllData()
        noteVm.getAllData.observe(viewLifecycleOwner) { data ->
            commonvm.checkIfDataEmpty(data)
            noteAdapter.setData(data)
        }
    }

    private fun searchItem() {
        binding.svList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchThroughDatabase(query)
                    binding.svList.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchThroughDatabase(newText)
                    binding.svList.clearFocus()
                }
                return true
            }

        })

    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        noteVm.searchNote(searchQuery)
        noteVm.searchNote.observeOnce(viewLifecycleOwner) { list ->
            list?.let {
                noteAdapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}