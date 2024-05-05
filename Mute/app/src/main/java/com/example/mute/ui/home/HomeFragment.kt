package com.example.mute.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

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

        myListAdapter.submitList(
            listOf(
                HomeItem("", "리스트1", ItemType.MY_LIST),
                HomeItem("", "리스트2", ItemType.MY_LIST)
            )
        )

        musicalAdapter.submitList(
            listOf(
                HomeItem("", "구텐버그", ItemType.MUSICAL),
                HomeItem("", "스토리오브마이라이프", ItemType.MUSICAL),
                HomeItem("", "해적", ItemType.MUSICAL),
                HomeItem("", "하데스타운", ItemType.MUSICAL),
                HomeItem("", "Trace U", ItemType.MUSICAL)
            )
        )

        actorAdapter.submitList(
            listOf(
                HomeItem("", "정욱진", ItemType.ACTOR),
                HomeItem("", "김려원", ItemType.ACTOR),
                HomeItem("", "김이후", ItemType.ACTOR),
                HomeItem("", "박강현", ItemType.ACTOR),
                HomeItem("", "기세중", ItemType.ACTOR),
                HomeItem("", "최호승", ItemType.ACTOR),
                HomeItem("", "최수진", ItemType.ACTOR)
            )
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}