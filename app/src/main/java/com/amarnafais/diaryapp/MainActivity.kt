package com.amarnafais.diaryapp

import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.amarnafais.diaryapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var diaryDatabase: DiaryDatabase
    private lateinit var diaryAdapter: DiaryAdapter
    private var selectedDate: String = ""
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView
        diaryAdapter = DiaryAdapter(
            listOf(),
            this::onEntryClick,
            this::onEntryLongPress
        )
        binding.rvEntries.layoutManager = GridLayoutManager(this, 2)
        binding.rvEntries.adapter = diaryAdapter

        // Initialize Room database
        diaryDatabase = DiaryDatabase.getInstance(this)

        // Set up CalendarView Date Selection
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            binding.tvTodayDate.text = "Entries for $selectedDate"
            loadEntriesForDate(selectedDate)
        }

        // Set up Floating Action Button
        binding.fabAddEntry.setOnClickListener {
            if (selectedDate.isNotEmpty()) {
                val intent = Intent(this, WriteEntryActivity::class.java)
                intent.putExtra("selected_date", selectedDate)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show()
            }
        }

        // Default to today's date
        selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        binding.tvTodayDate.text = "Entries for $selectedDate"
        loadEntriesForDate(selectedDate)
    }

    override fun onResume() {
        super.onResume()
        // Reload entries for the selected date
        loadEntriesForDate(selectedDate)
    }

    private fun loadEntriesForDate(date: String) {
        diaryDatabase.diaryDao().getEntriesByDate(date).observe(this) { entries ->
            diaryAdapter.updateEntries(entries)
        }
    }

    private fun onEntryClick(entry: DiaryEntry) {
        // Navigate to EditEntryActivity
        val intent = Intent(this, EditEntryActivity::class.java)
        intent.putExtra("entry_id", entry.id)
        startActivity(intent)
    }

    private fun onEntryLongPress(entry: DiaryEntry) {
        if (actionMode != null) return

        actionMode = startActionMode(actionModeCallback)
        diaryAdapter.toggleSelection(entry)
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.contextual_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.menu_delete -> {
                    deleteSelectedEntries()
                    mode?.finish()
                    return true
                }
            }
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            diaryAdapter.clearSelection()
        }
    }

    private fun deleteSelectedEntries() {
        val selectedEntries = diaryAdapter.getSelectedEntries()

        // Extract IDs of selected entries
        val selectedIds = selectedEntries.map { it.id }

        // Delete entries from the database
        lifecycleScope.launch {
            diaryDatabase.diaryDao().deleteEntriesByIds(selectedIds)
            Toast.makeText(this@MainActivity, "Selected entries deleted", Toast.LENGTH_SHORT).show()

            // Update the adapter after deletion
            diaryAdapter.removeSelectedEntries()
        }
    }

}