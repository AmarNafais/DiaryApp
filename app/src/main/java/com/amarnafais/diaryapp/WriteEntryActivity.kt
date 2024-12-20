package com.amarnafais.diaryapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amarnafais.diaryapp.databinding.ActivityWriteEntryBinding
import kotlinx.coroutines.launch

class WriteEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWriteEntryBinding
    private lateinit var diaryDatabase: DiaryDatabase
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up View Binding
        binding = ActivityWriteEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room database
        diaryDatabase = DiaryDatabase.getInstance(this)

        // Get the selected date from the intent
        selectedDate = intent.getStringExtra("selected_date") ?: ""

        // Handle Submit Button click
        binding.btnSubmit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etStory.text.toString()
            if (title.isNotBlank() && content.isNotBlank()) {
                saveEntry(title, content)
            } else {
                Toast.makeText(this, "Please fill in both title and content", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Back Button click
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun saveEntry(title: String, content: String) {
        val newEntry = DiaryEntry(title = title, content = content, date = selectedDate)

        lifecycleScope.launch {
            diaryDatabase.diaryDao().insert(newEntry)
            Toast.makeText(this@WriteEntryActivity, "Entry saved", Toast.LENGTH_SHORT).show()
            finish() // Go back to MainActivity
        }
    }
}
