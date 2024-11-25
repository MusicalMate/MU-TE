package com.example.mute.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentSearchBinding
import com.example.mute.ui.ContentItemClickListener
import com.example.mute.ui.DetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()

    private lateinit var detailPhotoAdapter: DetailAdapter
    private lateinit var detailVideoAdapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchStatus = viewModel.searchStatus.value
        binding.searchResult = viewModel.searchResult.value

        setSearchBarView()
        initAdapter()
        setObserver()
        setListener()
    }

    private fun setSearchBarView() {
        binding.apply {
            viewSearch.setupWithSearchBar(barSearch)
            viewSearch.editText.setOnEditorActionListener { p0, _, _ ->
                val text = p0?.text.toString().replace(" ", "")
                viewSearch.hide()
                barSearch.setText(text)
                viewModel.searchKeyword(text)
                true
            }
        }
    }

    private fun initAdapter() {
        val photoClickListener = ContentItemClickListener { contentInfo ->
            val action = SearchFragmentDirections.actionSearchFragmentToImageDetailFragment(
                contentInfo.contentId
            )
            findNavController().navigate(action)
        }
        val videoClickListener = ContentItemClickListener { contentInfo ->
            val action = SearchFragmentDirections.actionSearchFragmentToVideoPlayFragment(
                contentInfo.contentId
            )
            findNavController().navigate(action)
        }

        detailPhotoAdapter = DetailAdapter(photoClickListener)
        binding.rvSearchPhoto.adapter = detailPhotoAdapter
        detailVideoAdapter = DetailAdapter(videoClickListener)
        binding.rvSearchVideo.adapter = detailVideoAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchStatus.collectLatest { searchStatus ->
                    if (searchStatus == SearchStatus.ERROR) {
                        Toast.makeText(requireContext(), "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
                    }
                    binding.searchStatus = searchStatus
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searchResult.collectLatest { searchResult ->
                    binding.searchResult = searchResult
                    detailPhotoAdapter.submitList(searchResult.imageInfo)
                    detailVideoAdapter.submitList(searchResult.videoInfo)
                }
            }
        }
    }

    private fun setListener() {
        binding.layoutSearchType.setOnClickListener {
            val action = if (viewModel.searchResult.value.type == "actor") {
                SearchFragmentDirections.actionSearchFragmentToActorDetailFragment(viewModel.searchResult.value.playListId.toString())
            } else {
                SearchFragmentDirections.actionSearchFragmentToMusicalDetailFragment(viewModel.searchResult.value.playListId.toString())
            }
            findNavController().navigate(action)
        }

        binding.tvSearchVideoAll.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToAllVideoFragment(
                videoItems = viewModel.searchResult.value.videoInfo.toTypedArray(),
                playListId = viewModel.searchResult.value.playListId,
                name = viewModel.searchResult.value.name
            )
            findNavController().navigate(action)
        }

        binding.tvSearchPhotoAll.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToAllPhotoFragment(
                photoItems = viewModel.searchResult.value.imageInfo.toTypedArray(),
                playListId = viewModel.searchResult.value.playListId,
                name = viewModel.searchResult.value.name
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}