package com.example.mute.ui.filePlay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentVideoPlayBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoPlayFragment : Fragment() {

    private var _binding: FragmentVideoPlayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoPlayViewModel by viewModels()
    private val args: VideoPlayFragmentArgs by navArgs()
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoPlayBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPlayer()
        setObserver()
        setListener()
        viewModel.getContentDetailInfo(args.contentId)
    }

    private fun setPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        binding.pvVideoPlay.player = exoPlayer
    }

    @OptIn(UnstableApi::class)
    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contentDetailInfo.collectLatest { contentDetailInfo ->
                binding.contentDetailInfo = contentDetailInfo
                val videoItem = MediaItem.fromUri(contentDetailInfo.contentUrl)
                val mediaSource = ProgressiveMediaSource
                    .Factory(DefaultDataSource.Factory(requireContext()))
                    .createMediaSource(videoItem)
                exoPlayer.setMediaSource(mediaSource)
                exoPlayer.prepare()
                exoPlayer.play()
                binding.pvVideoPlayExoController.player = exoPlayer
            }
        }
    }

    private fun setListener() {
        binding.layoutVideoPlayTop.setOnClickListener { view ->
            view.visibility = View.GONE
        }

        binding.pvVideoPlay.setOnClickListener {
            binding.layoutVideoPlayTop.visibility = View.VISIBLE
        }

        binding.ivVideoPlayBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        _binding = null
        exoPlayer.release()
        super.onDestroyView()
    }
}