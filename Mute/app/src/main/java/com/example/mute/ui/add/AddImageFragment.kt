package com.example.mute.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAddImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddImageFragment : Fragment() {

    private var _binding: FragmentAddImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddImageViewModel by activityViewModels()
    private val args: AddImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddImageBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val absolutePath = args.absolutePath
        val selectedActor = args.selectedActor
        if (absolutePath != null) {
            viewModel.setImage(absolutePath)
        }
        if (selectedActor != null) {
            viewModel.setSelectedActor(selectedActor)
        }

        setListener()
        setObserver()

        binding.executePendingBindings()
    }

    private fun setListener() {
        binding.tvAddImageCancel.setOnClickListener {
            findNavController().navigateUp()
            Toast.makeText(requireContext(), "업로드 취소", Toast.LENGTH_SHORT).show()
            viewModel.clearAll()
        }

        binding.tvAddImageSelectActor.setOnClickListener {
            viewModel.getActors()
            val actors = viewModel.musicalActors.value
            if (actors != null) {
                if (actors.isNotEmpty()) {
                    val action =
                        AddImageFragmentDirections.actionAddImageFragmentToAddActorFragment(
                            contentType = "image",
                            musicalActors = actors.toTypedArray(),
                            musicalTitle = viewModel.performanceTitle.value,
                            actorId = viewModel.selectedActor.value?.actorId ?: "-1"
                        )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "뮤지컬 제목을 확인해주세요.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.tvAddImageChangeActor.setOnClickListener {
            val action = AddImageFragmentDirections.actionAddImageFragmentToAddActorFragment(
                contentType = "image",
                musicalActors = viewModel.musicalActors.value!!.toTypedArray(),
                musicalTitle = viewModel.performanceTitle.value,
                actorId = viewModel.selectedActor.value?.actorId ?: "-1"
            )
            findNavController().navigate(action)
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fileInputStatus.collectLatest { fileInputStatus ->
                when (fileInputStatus) {
                    FileInputStatus.MUSICAL_TITLE_NOT_ENTERED, FileInputStatus.FILE_TITLE_NOT_ENTERED, FileInputStatus.PERFORMANCE_TIME_NOT_SELECTED -> {
                        Toast.makeText(
                            requireContext(),
                            fileInputStatus.description,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uploadStatus.collectLatest { uploadStatus ->
                when (uploadStatus) {
                    UploadStatus.SUCCESS -> {
                        Toast.makeText(requireContext(), "업로드 성공", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }

                    UploadStatus.FAILURE -> {
                        Toast.makeText(requireContext(), "업로드 실패", Toast.LENGTH_SHORT).show()
                    }

                    UploadStatus.IN_PROGRESS -> {
                        Toast.makeText(requireContext(), "업로드 진행 중", Toast.LENGTH_SHORT).show()
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}