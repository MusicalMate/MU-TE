package com.example.mute.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentMyListDetailBinding

class MyListDetailFragment : Fragment() {

    private var _binding: FragmentMyListDetailBinding? = null
    private val binding get() = _binding!!
    private val args: MyListDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyListDetailBinding.inflate(layoutInflater, container, false)
        binding.myListName = args.myListName

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}