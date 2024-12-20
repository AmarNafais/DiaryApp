package com.amarnafais.diaryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amarnafais.diaryapp.databinding.ItemDiaryEntryBinding

class DiaryAdapter(
    private var entries: List<DiaryEntry>,
    private val onEditClick: (DiaryEntry) -> Unit,
    private val onLongPress: (DiaryEntry) -> Unit
) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    private val selectedEntries = mutableSetOf<DiaryEntry>()
    private var selectionMode = false

    inner class DiaryViewHolder(private val binding: ItemDiaryEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: DiaryEntry) {
            binding.tvDate.text = entry.date
            binding.tvTitle.text = entry.title
            binding.tvContent.text = entry.content

            // Highlight selection
            val isSelected = selectedEntries.contains(entry)
            binding.root.setBackgroundColor(
                if (isSelected) ContextCompat.getColor(binding.root.context, android.R.color.holo_blue_light)
                else ContextCompat.getColor(binding.root.context, android.R.color.white)
            )

            // Show or hide the check icon
            binding.ivCheck.visibility = if (isSelected) View.VISIBLE else View.GONE

            // Click listener
            binding.root.setOnClickListener {
                if (selectionMode) {
                    toggleSelection(entry)
                } else {
                    onEditClick(entry)
                }
            }

            // Long-press listener
            binding.root.setOnLongClickListener {
                selectionMode = true
                toggleSelection(entry)
                onLongPress(entry)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val binding = ItemDiaryEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount() = entries.size

    fun updateEntries(newEntries: List<DiaryEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }

    fun removeSelectedEntries() {
        entries = entries.filter { it !in selectedEntries }
        selectedEntries.clear()
        selectionMode = false
        notifyDataSetChanged()
    }

    fun toggleSelection(entry: DiaryEntry) {
        if (selectedEntries.contains(entry)) {
            selectedEntries.remove(entry)
        } else {
            selectedEntries.add(entry)
        }

        if (selectedEntries.isEmpty()) {
            selectionMode = false
        }

        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedEntries.clear()
        selectionMode = false
        notifyDataSetChanged()
    }

    fun getSelectedEntries(): List<DiaryEntry> {
        return selectedEntries.toList()
    }
}
