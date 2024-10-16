package com.example.mute.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.exoplayer.ExoPlayer
import com.example.mute.R
import com.example.mute.databinding.FragmentVideoPlayBinding

class VideoPlayFragment : Fragment() {

    private var _binding: FragmentVideoPlayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VideoPlayViewModel by viewModels()

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
    }

    private fun setPlayer() {
        val player = ExoPlayer.Builder(requireContext()).build()
        binding.pvVideoPlay.player = player
        val firstItem = MediaItem.fromUri("")
        player.addMediaItem(firstItem)
        player.prepare()
        player.play()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}