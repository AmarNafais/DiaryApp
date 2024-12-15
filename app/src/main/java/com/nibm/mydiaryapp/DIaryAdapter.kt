package com.nibm.mydiaryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nibm.mydiaryapp.databinding.DiaryEntryBlockBinding

class DiaryAdapter(
    private val diaryEntries: MutableList<Pair<String, String>>, // Title, Content Pair
    private val onItemClickListener: (Int) -> Unit // Lambda to handle click events
) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    // ViewHolder to hold the references to each item view
    class DiaryViewHolder(val binding: DiaryEntryBlockBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        // Inflate the custom layout for the diary entry block
        val binding = DiaryEntryBlockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val entry = diaryEntries[position]

        // Set the title and content from the entry
        holder.binding.tvEntryTitle.text = entry.first
        holder.binding.tvEntryContent.text = entry.second

        // Set the click listener to handle click events on the entry
        holder.itemView.setOnClickListener {
            onItemClickListener(position) // Pass the clicked item position
        }
    }

    override fun getItemCount(): Int = diaryEntries.size
}
