package com.example.mute.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentHomeAllBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAllFragment : Fragment() {

    private var _binding: FragmentHomeAllBinding? = null
    private val binding get() = _binding!!
    private val args: HomeAllFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAllBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.tbHomeAll.title = args.itemType
        initAdapter()
    }

    private fun initAdapter() {
        val clickListener = HomeItemClickListener { homeItem ->
            val action = when (homeItem.itemType) {
                ItemType.MUSICAL -> {
                    HomeAllFragmentDirections.actionHomeAllFragmentToMusicalDetailFragment(homeItem.playListId)
                }

                ItemType.ACTOR -> {
                    HomeAllFragmentDirections.actionHomeAllFragmentToActorDetailFragment(homeItem.playListId)
                }
            }
            findNavController().navigate(action)
        }

        val allListAdapter = HomeAdapter(clickListener)
        binding.rvHomeAll.adapter = allListAdapter
        allListAdapter.submitList(args.HomeItems.toList())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}