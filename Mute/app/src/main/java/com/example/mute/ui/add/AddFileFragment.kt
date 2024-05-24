package com.example.mute.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mute.R
import com.example.mute.databinding.FragmentAddFileBinding

class AddFileFragment : Fragment() {

    private var _binding: FragmentAddFileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}