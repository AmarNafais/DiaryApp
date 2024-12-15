package com.nibm.mydiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nibm.mydiaryapp.databinding.FragmentEditEntryBinding

class EditEntryFragment : Fragment() {

    private lateinit var binding: FragmentEditEntryBinding
    private var position: Int = -1
    private var listener: OnEntryEditedListener? = null

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"
        private const val ARG_POSITION = "arg_position"

        fun newInstance(title: String, content: String, position: Int): EditEntryFragment {
            val fragment = EditEntryFragment()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_CONTENT, content)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    interface OnEntryEditedListener {
        fun onEntryEdited(position: Int, title: String, content: String)
    }

    fun setOnEntryEditedListener(listener: OnEntryEditedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditEntryBinding.inflate(inflater, container, false)

        // Get arguments
        val title = arguments?.getString(ARG_TITLE) ?: ""
        val content = arguments?.getString(ARG_CONTENT) ?: ""
        position = arguments?.getInt(ARG_POSITION) ?: -1

        // Set existing values
        binding.editTextTitle.setText(title)
        binding.editTextContent.setText(content)

        // Save button click listener
        binding.buttonSaveEditedEntry.setOnClickListener {
            val updatedTitle = binding.editTextTitle.text.toString()
            val updatedContent = binding.editTextContent.text.toString()

            // Notify listener
            if (position != -1 && listener != null) {
                listener?.onEntryEdited(position, updatedTitle, updatedContent)
                parentFragmentManager.popBackStack() // Go back to HomeFragment
            }
        }

        return binding.root
    }
}