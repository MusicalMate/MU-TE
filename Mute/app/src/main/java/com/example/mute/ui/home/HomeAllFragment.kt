package com.example.mute.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentHomeAllBinding

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

        binding.tbHomeAll.title = args.itemType
        initAdapter()
    }

    private fun initAdapter() {
        val clickListener = HomeItemClickListener { homeItem ->
            val action = when (homeItem.itemType) {
                ItemType.MY_LIST -> {
                    HomeAllFragmentDirections.actionHomeAllFragmentToMyListDetailFragment(homeItem.name)
                }

                ItemType.MUSICAL -> {
                    HomeAllFragmentDirections.actionHomeAllFragmentToMusicalDetailFragment(homeItem.name)
                }

                ItemType.ACTOR -> {
                    HomeAllFragmentDirections.actionHomeAllFragmentToActorDetailFragment(homeItem.name)
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