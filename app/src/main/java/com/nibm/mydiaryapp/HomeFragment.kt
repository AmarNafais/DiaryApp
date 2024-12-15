package com.nibm.mydiaryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nibm.mydiaryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val diaryEntries = listOf(
        "Lorem ipsum dolor sit amet, consectetur dolor sit amet",
        "Lorem ipsum dolor sit amet, consectetur dolor sit amet",
        "Lorem ipsum dolor sit amet, consectetur dolor sit amet",
        "Lorem ipsum dolor sit amet, consectetur dolor sit amet"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.recyclerViewEntries.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEntries.adapter = DiaryAdapter(diaryEntries)
        return binding.root
    }
}