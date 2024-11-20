package com.example.mute.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var musicalAdapter: HomeAdapter
    private lateinit var actorAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHomeInfo()
        initAdapter()
        setObserver()
        setAllClickListener()
    }

    private fun initAdapter() {
        val clickListener = HomeItemClickListener { homeItem ->
            val action = when (homeItem.itemType) {

                ItemType.MUSICAL -> {
                    HomeFragmentDirections.actionHomeFragmentToMusicalDetailFragment(homeItem.playListId)
                }

                ItemType.ACTOR -> {
                    HomeFragmentDirections.actionHomeFragmentToActorDetailFragment(homeItem.playListId)
                }
            }
            findNavController().navigate(action)
        }

        musicalAdapter = HomeAdapter(clickListener)
        binding.rvHomeMusical.adapter = musicalAdapter

        actorAdapter = HomeAdapter(clickListener)
        binding.rvHomeActor.adapter = actorAdapter

        musicalAdapter.submitList(viewModel.musicalList.value)
        actorAdapter.submitList(viewModel.actorList.value)
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.musicalList.collectLatest { musicalList ->
                    musicalAdapter.submitList(musicalList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.actorList.collectLatest { actorList ->
                    actorAdapter.submitList(actorList)
                }
            }
        }
    }

    private fun setAllClickListener() {
        binding.apply {
            tvHomeActorAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeAllFragment(
                    "배우",
                    viewModel.actorList.value.toTypedArray()
                )
                findNavController().navigate(action)
            }
            tvHomeMusicalAll.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToHomeAllFragment(
                    "뮤지컬",
                    viewModel.musicalList.value.toTypedArray()
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