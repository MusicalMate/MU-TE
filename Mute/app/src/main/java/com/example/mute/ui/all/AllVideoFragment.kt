package com.example.mute.ui.all

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
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAllVideoBinding
import com.example.mute.model.ContentType
import com.example.mute.ui.ContentAdapter
import com.example.mute.ui.ContentItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllVideoFragment : Fragment() {

    private var _binding: FragmentAllVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllVideoViewModel by viewModels()
    private val args: AllVideoFragmentArgs by navArgs()

    private lateinit var allVideoAdapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllVideoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.setViewModelState(args.videoItems.toList(), args.playListId, args.name)
        initAdapter()
        setObserver()
    }

    private fun initAdapter() {
        val clickListener = ContentItemClickListener { contentItem ->
            val action =
                AllVideoFragmentDirections.actionAllVideoFragmentToVideoPlayFragment(contentItem.contentId)
            findNavController().navigate(action)
        }

        allVideoAdapter = ContentAdapter(ContentType.VIDEO, clickListener)
        binding.rvAllVideoVideo.adapter = allVideoAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentListState.collectLatest { viewModelState ->
                    allVideoAdapter.submitList(viewModelState.contentList)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}