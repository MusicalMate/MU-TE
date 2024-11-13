package com.example.mute.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAddVideoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddVideoFragment : Fragment() {

    private var _binding: FragmentAddVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddVideoViewModel by viewModels()
    private val args: AddVideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddVideoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setVideo(args.absolutePath)

        setVideoView()
    }

    private fun setVideoView(){
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.vvAddVideo)
        binding.vvAddVideo.apply {
            setMediaController(mediaController)
            setVideoPath(args.absolutePath)
            requestFocus()
            setOnPreparedListener {
                this.seekTo(0)
                this.pause()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}