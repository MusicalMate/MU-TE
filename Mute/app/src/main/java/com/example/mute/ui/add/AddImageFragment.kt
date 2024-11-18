package com.example.mute.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val viewModel: AddImageViewModel by viewModels()
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

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setImage(args.absolutePath)
        setListener()
        setObserver()
    }

    private fun setListener() {
        binding.tvAddImageCancel.setOnClickListener {
            findNavController().navigateUp()
            Toast.makeText(requireContext(), "업로드 취소", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uploadStatus.collectLatest { uploadStatus ->
                when (uploadStatus) {
                    UploadStatus.SUCCESS -> {
                        findNavController().navigateUp()
                        Toast.makeText(requireContext(), "업로드 성공", Toast.LENGTH_SHORT).show()
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