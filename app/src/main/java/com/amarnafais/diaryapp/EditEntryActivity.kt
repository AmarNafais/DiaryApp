package com.amarnafais.diaryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amarnafais.diaryapp.databinding.ActivityEditEntryBinding
import kotlinx.coroutines.launch

class EditEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEntryBinding
    private lateinit var diaryDatabase: DiaryDatabase
    private var entryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up View Binding
        binding = ActivityEditEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database
        diaryDatabase = DiaryDatabase.getInstance(this)

        // Get the entry ID passed from MainActivity
        entryId = intent.getIntExtra("entry_id", -1)
        if (entryId != -1) {
            loadDiaryEntry(entryId)
        } else {
            Toast.makeText(this, "Error loading entry", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Handle Save Button click
        binding.btnSave.setOnClickListener {
            val updatedTitle = binding.etTitle.text.toString()
            val updatedContent = binding.etContent.text.toString()
            if (updatedTitle.isNotBlank() && updatedContent.isNotBlank()) {
                updateEntry(updatedTitle, updatedContent)
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Back Button click
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun loadDiaryEntry(entryId: Int) {
        lifecycleScope.launch {
            val entry = diaryDatabase.diaryDao().getEntryById(entryId)
            entry?.let {
                binding.etTitle.setText(it.title)
                binding.etContent.setText(it.content)
            }
        }
    }

    private fun updateEntry(title: String, content: String) {
        lifecycleScope.launch {
            val entry = diaryDatabase.diaryDao().getEntryById(entryId)
            entry?.let {
                val updatedEntry = it.copy(title = title, content = content)
                diaryDatabase.diaryDao().update(updatedEntry)
                Toast.makeText(this@EditEntryActivity, "Entry updated", Toast.LENGTH_SHORT).show()
                finish() // Go back to MainActivity
            }
        }
    }
}
