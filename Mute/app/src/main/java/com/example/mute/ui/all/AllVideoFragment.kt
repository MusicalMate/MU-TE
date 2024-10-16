package com.example.mute.ui.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAllVideoBinding
import com.example.mute.ui.ContentAdapter
import com.example.mute.ui.ContentItem
import com.example.mute.ui.ContentItemClickListener
import com.example.mute.ui.ContentType

class AllVideoFragment : Fragment() {

    private var _binding: FragmentAllVideoBinding? = null
    private val binding get() = _binding!!
    private val args: AllVideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllVideoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        val clickListener = ContentItemClickListener { contentItem ->
            val action = AllVideoFragmentDirections.actionAllVideoFragmentToVideoPlayFragment()
            findNavController().navigate(action)
        }

        val allVideoAdapter = ContentAdapter(ContentType.VIDEO, clickListener)
        binding.rvAllVideoVideo.adapter = allVideoAdapter
        allVideoAdapter.submitList(args.videoItems.toList())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}