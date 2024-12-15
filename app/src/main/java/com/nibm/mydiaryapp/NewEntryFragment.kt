package com.nibm.mydiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nibm.mydiaryapp.databinding.FragmentNewEntryBinding

class NewEntryFragment : Fragment() {

    private lateinit var binding: FragmentNewEntryBinding
    private var onNewEntryAddedListener: OnNewEntryAddedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewEntryBinding.inflate(inflater, container, false)

        // Handle Save button click
        binding.buttonSaveEntry.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val content = binding.editTextContent.text.toString().trim()

            if (title.isNotEmpty() && content.isNotEmpty()) {
                onNewEntryAddedListener?.onNewEntryAdded(title, content)
                parentFragmentManager.popBackStack() // Navigate back to HomeFragment
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // Interface for communicating with HomeFragment
    interface OnNewEntryAddedListener {
        fun onNewEntryAdded(title: String, content: String)
    }

    fun setOnNewEntryAddedListener(listener: OnNewEntryAddedListener) {
        this.onNewEntryAddedListener = listener
    }
}