package com.amarnafais.diaryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.amarnafais.diaryapp.databinding.ActivityDiaryDetailsBinding
import kotlinx.coroutines.launch

class DiaryDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryDetailsBinding
    private lateinit var diaryDatabase: DiaryDatabase
    private var entryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up View Binding
        binding = ActivityDiaryDetailsBinding.inflate(layoutInflater)
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

        // Handle Edit Button click
        binding.ivEdit.setOnClickListener {
            val intent = Intent(this, EditEntryActivity::class.java)
            intent.putExtra("entry_id", entryId)
            startActivity(intent)
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
                binding.tvTitle.text = it.title
                binding.tvDate.text = it.date
                binding.tvContent.text = it.content
            }
        }
    }
}
