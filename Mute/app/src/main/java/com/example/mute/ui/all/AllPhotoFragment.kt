package com.example.mute.ui.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAllPhotoBinding
import com.example.mute.model.ContentType
import com.example.mute.ui.ContentAdapter
import com.example.mute.ui.ContentItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllPhotoFragment : Fragment() {

    private var _binding: FragmentAllPhotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AllPhotoViewModel by viewModels()
    private val args: AllPhotoFragmentArgs by navArgs()

    private lateinit var allPhotoAdapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPhotoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.setViewModelState(args.photoItems.toList(), args.playListId, args.name)
        initAdapter()
        setObserver()
    }

    private fun initAdapter() {
        val clickListener = ContentItemClickListener { contentItem ->
            Log.d("contentItemClick - Photo", contentItem.contentTitle)
            // TODO - 데이터 요청
        }

        allPhotoAdapter = ContentAdapter(ContentType.PHOTO, clickListener)
        binding.rvAllPhotoPhoto.adapter = allPhotoAdapter
        allPhotoAdapter.submitList(args.photoItems.toList())
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentListState.collectLatest { viewModelState ->
                    allPhotoAdapter.submitList(viewModelState.contentList)
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}