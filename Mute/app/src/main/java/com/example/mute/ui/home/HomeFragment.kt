package com.example.mute.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setAllClickListener()
    }

    private fun initAdapter() {
        val clickListener = HomeItemClickListener { homeItem ->
            val action = when (homeItem.itemType) {
                ItemType.MY_LIST -> {
                    HomeFragmentDirections.actionHomeFragmentToMyListDetailFragment(homeItem.name)
                }

                ItemType.MUSICAL -> {
                    HomeFragmentDirections.actionHomeFragmentToMusicalDetailFragment(homeItem.name)
                }

                ItemType.ACTOR -> {
                    HomeFragmentDirections.actionHomeFragmentToActorDetailFragment(homeItem.name)
                }
            }
            findNavController().navigate(action)
        }

        val myListAdapter = HomeAdapter(clickListener)
        binding.rvHomeMylist.adapter = myListAdapter

        val musicalAdapter = HomeAdapter(clickListener)
        binding.rvHomeMusical.adapter = musicalAdapter

        val actorAdapter = HomeAdapter(clickListener)
        binding.rvHomeActor.adapter = actorAdapter

        myListAdapter.submitList(viewModel.myList)
        musicalAdapter.submitList(viewModel.musicalList)
        actorAdapter.submitList(viewModel.actorList)
    }

    private fun setAllClickListener() {
        binding.apply {
            tvHomeMylistAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeAllFragment(
                    "마이리스트",
                    viewModel.myList.toTypedArray()
                )
                findNavController().navigate(action)
            }
            tvHomeActorAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeAllFragment(
                    "배우",
                    viewModel.actorList.toTypedArray()
                )
                findNavController().navigate(action)
            }
            tvHomeMusicalAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeAllFragment(
                    "뮤지컬",
                    viewModel.musicalList.toTypedArray()
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}