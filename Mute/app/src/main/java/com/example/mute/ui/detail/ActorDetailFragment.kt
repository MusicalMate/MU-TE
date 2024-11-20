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
import com.example.mute.ui.ContentItemClickListener
import com.example.mute.ui.DetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorDetailFragment : Fragment() {

    private var _binding: FragmentActorDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ActorDetailViewModel by viewModels()
    private val args: ActorDetailFragmentArgs by navArgs()

    private lateinit var detailPhotoAdapter: DetailAdapter
    private lateinit var detailVideoAdapter: DetailAdapter

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
        binding.viewModel = viewModel

        initAdapter()
        setObserver()
        setListener()
        viewModel.getActorInfo(args.actorId)
    }

    private fun initAdapter() {
        val photoClickListener = ContentItemClickListener {
            // TODO: 사진 요청
        }
        val videoClickListener = ContentItemClickListener { contentInfo ->
            val action = ActorDetailFragmentDirections.actionActorDetailFragmentToVideoPlayFragment(
                contentInfo.contentId
            )
            findNavController().navigate(action)
        }

        detailPhotoAdapter = DetailAdapter(photoClickListener)
        binding.rvActorDetailPhoto.adapter = detailPhotoAdapter
        detailVideoAdapter = DetailAdapter(videoClickListener)
        binding.rvActorDetailVideo.adapter = detailVideoAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorDetailInfo.collectLatest { actorDetailInfo ->
                binding.actorDetailInfo = actorDetailInfo
                detailPhotoAdapter.submitList(actorDetailInfo.imageInfo)
                detailVideoAdapter.submitList(actorDetailInfo.videoInfo)
            }
        }
    }

    private fun setListener() {
        binding.tvActorDetailVideoAll.setOnClickListener {
            val action = ActorDetailFragmentDirections.actionActorDetailFragmentToAllVideoFragment(
                videoItems = viewModel.actorDetailInfo.value.videoInfo.toTypedArray(),
                playListId = viewModel.actorDetailInfo.value.actorPlayListId,
                name = viewModel.actorDetailInfo.value.actorName
            )
            findNavController().navigate(action)
        }

        binding.tvActorDetailPhotoAll.setOnClickListener {
            val action = ActorDetailFragmentDirections.actionActorDetailFragmentToAllPhotoFragment(
                photoItems = viewModel.actorDetailInfo.value.imageInfo.toTypedArray(),
                playListId = viewModel.actorDetailInfo.value.actorPlayListId,
                name = viewModel.actorDetailInfo.value.actorName
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}