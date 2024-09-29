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
import com.example.mute.databinding.FragmentActorDetailBinding
import com.example.mute.ui.ContentItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ActorDetailFragment : Fragment() {

    private var _binding: FragmentActorDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActorDetailViewModel by viewModels { ActorDetailViewModel.Factory }
    private val args: ActorDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        setObserver()
        setListener()
        viewModel.getActorInfo(args.actorName)
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorDetailInfo.collectLatest { actorDetailInfo ->
                binding.actorDetailInfo = actorDetailInfo
            }
        }
    }

    private fun setListener() {
        binding.tvActorDetailVideoAll.setOnClickListener {
            val action = ActorDetailFragmentDirections.actionActorDetailFragmentToAllVideoFragment(
                listOf<ContentItem>().toTypedArray()
            )
            findNavController().navigate(action)
        }

        binding.tvActorDetailPhotoAll.setOnClickListener {
            val action = ActorDetailFragmentDirections.actionActorDetailFragmentToAllPhotoFragment(
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