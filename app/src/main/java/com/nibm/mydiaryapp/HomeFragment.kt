package com.nibm.mydiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nibm.mydiaryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), NewEntryFragment.OnNewEntryAddedListener{

    private lateinit var binding: FragmentHomeBinding
    private lateinit var dbHelper: DatabaseHelper // DatabaseHelper instance
    private val diaryEntries = mutableListOf<Pair<String, String>>() // List of Title-Content pairs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Initialize Database Helper
        dbHelper = DatabaseHelper(requireContext())

        // FAB to open NewEntryFragment
        binding.fabAddEntry.setOnClickListener {
            val newEntryFragment = NewEntryFragment()
            newEntryFragment.setOnNewEntryAddedListener(this)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newEntryFragment)
                .addToBackStack(null)
                .commit()
        }

        // Populate existing entries from SQLite database
        retrieveEntriesFromDatabase()

        return binding.root
    }

    // Handle new entry from NewEntryFragment
    override fun onNewEntryAdded(title: String, content: String) {
        // Insert new entry into SQLite database
        dbHelper.insertEntry(title, content)

        // Add the new entry to the list
        diaryEntries.add(Pair(title, content))

        // Re-populate the grid layout with updated entries
        populateGridLayout()
    }




    // Populate the GridLayout with diary entries
    private fun populateGridLayout() {
        val gridLayout = binding.gridLayoutEntries
        gridLayout.removeAllViews() // Clear previous views

        for (entry in diaryEntries) {
            val entryView = layoutInflater.inflate(R.layout.diary_entry_block, gridLayout, false)

            val titleTextView = entryView.findViewById<TextView>(R.id.tvEntryTitle)
            val contentTextView = entryView.findViewById<TextView>(R.id.tvEntryContent)

            titleTextView.text = entry.first
            contentTextView.text = entry.second

            entryView.setOnClickListener {
                // Navigate to EditEntryFragment (if editing functionality exists)
                val editEntryFragment = EditEntryFragment.newInstance(
                    entry.first, entry.second, diaryEntries.indexOf(entry)
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, editEntryFragment)
                    .addToBackStack(null)
                    .commit()
            }

            gridLayout.addView(entryView)
        }
    }

    // Retrieve existing entries from the database
    private fun retrieveEntriesFromDatabase() {
        // Clear existing entries in the list
        diaryEntries.clear()

        // Fetch entries from SQLite and add them to the list
        diaryEntries.addAll(dbHelper.getAllEntries())

        // Re-populate the grid layout with retrieved entries
        populateGridLayout()
    }
}
