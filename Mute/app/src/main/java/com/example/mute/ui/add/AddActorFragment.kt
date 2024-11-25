package com.example.mute.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAddActorBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddActorFragment : Fragment() {

    private var _binding: FragmentAddActorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddActorViewModel by viewModels()

    private val args: AddActorFragmentArgs by navArgs()
    private lateinit var addActorAdapter: AddActorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddActorBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initAdapter()
        setObserver()
        setListener()
        viewModel.setMusicalActorsInfo(args.musicalActors.toList(), args.actorId, args.musicalTitle)
    }

    private fun initAdapter() {
        val clickListener = ActorClickListener { actorSelected ->
            viewModel.selectActor(actorSelected.actorId)
        }

        addActorAdapter = AddActorAdapter(clickListener)
        binding.rvAddActor.adapter = addActorAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.musicalActors.collectLatest { musicalActors ->
                addActorAdapter.submitList(musicalActors)
            }
        }
    }

    private fun setListener() {
        binding.tvAddActorCancel.setOnClickListener {
            val action = if (args.contentType == "image") {
                AddActorFragmentDirections.actionAddActorFragmentToAddImageFragment(
                    absolutePath = null,
                    selectedActor = null
                )
            } else {
                AddActorFragmentDirections.actionAddActorFragmentToAddVideoFragment(
                    absolutePath = null,
                    selectedActor = null
                )
            }
            findNavController().navigate(action)
        }

        binding.tvAddActorSelect.setOnClickListener {
            val selectedActor = viewModel.getSelectedActor()
            if (selectedActor == null) {
                Toast.makeText(requireContext(), "배우를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val action = if (args.contentType == "image") {
                    AddActorFragmentDirections.actionAddActorFragmentToAddImageFragment(
                        absolutePath = null,
                        selectedActor = viewModel.getSelectedActor()
                    )
                } else {
                    AddActorFragmentDirections.actionAddActorFragmentToAddVideoFragment(
                        absolutePath = null,
                        selectedActor = viewModel.getSelectedActor()
                    )
                }
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}