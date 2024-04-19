package com.example.mute

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        val myListAdapter = HomeAdapter(emptyList())
        binding.rvHomeMylist.adapter = myListAdapter
        val musicalAdapter = HomeAdapter(emptyList())
        binding.rvHomeMusical.adapter = musicalAdapter
        val actorAdapter = HomeAdapter(emptyList())
        binding.rvHomeActor.adapter = actorAdapter
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}