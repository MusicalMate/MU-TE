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
import com.example.mute.databinding.FragmentMusicalDetailBinding
import com.example.mute.ui.ContentItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MusicalDetailFragment : Fragment() {

    private var _binding: FragmentMusicalDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MusicalDetailViewModel by viewModels()
    private val args: MusicalDetailFragmentArgs by navArgs()

    private lateinit var detailPhotoAdapter: DetailAdapter
    private lateinit var detailVideoAdapter: DetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicalDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initAdapter()
        setObserver()
        setListener()
        viewModel.getMusicalInfo(args.musicalId)
    }

    private fun initAdapter() {
        val photoClickListener = ContentItemClickListener {
            // TODO: 사진 요청
        }
        val videoClickListener = ContentItemClickListener { contentInfo ->
            val action =
                MusicalDetailFragmentDirections.actionMusicalDetailFragmentToVideoPlayFragment(
                    contentInfo.contentId
                )
            findNavController().navigate(action)
        }

        detailPhotoAdapter = DetailAdapter(photoClickListener)
        binding.rvMusicalDetailPhoto.adapter = detailPhotoAdapter
        detailVideoAdapter = DetailAdapter(videoClickListener)
        binding.rvMusicalDetailVideo.adapter = detailVideoAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.musicalDetailInfo.collectLatest { musicalDetailInfo ->
                binding.musicalDetailInfo = musicalDetailInfo
                detailPhotoAdapter.submitList(musicalDetailInfo.imageInfo)
                detailVideoAdapter.submitList(musicalDetailInfo.videoInfo)
            }
        }
    }

    private fun setListener() {
        binding.tvMusicalDetailVideoAll.setOnClickListener {
            val action =
                MusicalDetailFragmentDirections.actionMusicalDetailFragmentToAllVideoFragment(
                    viewModel.musicalDetailInfo.value.videoInfo.toTypedArray(),
                    star = viewModel.musicalDetailInfo.value.star,
                    playListId = viewModel.musicalDetailInfo.value.musicalPlayListId,
                    name = viewModel.musicalDetailInfo.value.musicalTitle
                )
            findNavController().navigate(action)
        }

        binding.tvMusicalDetailPhotoAll.setOnClickListener {
            val action =
                MusicalDetailFragmentDirections.actionMusicalDetailFragmentToAllPhotoFragment(
                    photoItems = viewModel.musicalDetailInfo.value.imageInfo.toTypedArray(),
                    star = viewModel.musicalDetailInfo.value.star,
                    playListId = viewModel.musicalDetailInfo.value.musicalPlayListId,
                    name = viewModel.musicalDetailInfo.value.musicalTitle
                )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}