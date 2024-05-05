package com.example.mute.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentMusicalDetailBinding

class MusicalDetailFragment : Fragment() {

    private var _binding: FragmentMusicalDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MusicalDetailViewModel by viewModels { MusicalDetailViewModel.Factory }
    private val args: MusicalDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicalDetailBinding.inflate(layoutInflater, container, false)
        binding.musicalName = args.musicalName
        viewModel.getMusicalInfo(args.musicalName)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}