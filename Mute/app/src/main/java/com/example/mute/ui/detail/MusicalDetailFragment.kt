package com.example.mute.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentMusicalDetailBinding
import com.example.mute.ui.ContentItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicalDetailFragment : Fragment() {

    private var _binding: FragmentMusicalDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MusicalDetailViewModel by viewModels()
    private val args: MusicalDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicalDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setObserver()
        setListener()
        viewModel.getMusicalInfo(args.musicalName)
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.musicalDetailInfo.collectLatest { musicalDetailInfo ->
                binding.musicalDetailInfo = musicalDetailInfo
            }
        }
    }

    private fun setListener() {
        binding.tvMusicalDetailVideoAll.setOnClickListener {
            val action =
                MusicalDetailFragmentDirections.actionMusicalDetailFragmentToAllVideoFragment(
                    listOf<ContentItem>().toTypedArray()
                )
            findNavController().navigate(action)
        }

        binding.tvMusicalDetailPhotoAll.setOnClickListener {
            val action =
                MusicalDetailFragmentDirections.actionMusicalDetailFragmentToAllPhotoFragment(
                    listOf<ContentItem>().toTypedArray()
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}