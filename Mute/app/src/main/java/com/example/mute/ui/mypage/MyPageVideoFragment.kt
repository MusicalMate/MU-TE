package com.example.mute.ui.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentMyPageVideoBinding
import com.example.mute.ui.ContentItemClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyPageVideoFragment : Fragment() {

    private var _binding: FragmentMyPageVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MyPageViewModel by activityViewModels()
    private lateinit var myPageAdapter: MyPageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyPageVideoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()
        setObserver()
        viewModel.getUserInfo()
    }

    private fun initAdapter() {
        val clickListener = ContentItemClickListener { contentInfo ->
            val action =
                MyPageVideoFragmentDirections.actionMyPageVideoFragmentToVideoPlayFragment(
                    contentInfo.contentId
                )
            findNavController().navigate(action)
        }

        val popUpClickListener =
            PopUpClickListener { contentId -> viewModel.deleteVideo(contentId) }
        myPageAdapter = MyPageAdapter(clickListener, popUpClickListener)
        binding.rvMyPageVideo.adapter = myPageAdapter
    }

    private fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userInfo.collectLatest { userInfo ->
                    myPageAdapter.submitList(userInfo.videoList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteStatus.collectLatest { deleteStatus ->
                    when (deleteStatus) {
                        DeleteStatus.DELETE_FAIL -> {
                            Toast.makeText(requireContext(), "삭제 실패", Toast.LENGTH_SHORT).show()
                            viewModel.setDeleteStatusReady()
                        }

                        DeleteStatus.DELETE_SUCCESS -> {
                            Toast.makeText(requireContext(), "삭제 완료", Toast.LENGTH_SHORT).show()
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}