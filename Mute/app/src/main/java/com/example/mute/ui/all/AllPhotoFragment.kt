package com.example.mute.ui.all

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mute.databinding.FragmentAllPhotoBinding
import com.example.mute.model.ContentType
import com.example.mute.ui.ContentAdapter
import com.example.mute.ui.ContentItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllPhotoFragment : Fragment() {

    private var _binding: FragmentAllPhotoBinding? = null
    private val binding get() = _binding!!
    private val args: AllPhotoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPhotoBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        val clickListener = ContentItemClickListener { contentItem ->
            Log.d("contentItemClick - Photo", contentItem.contentTitle)
            // TODO - 데이터 요청
        }

        val allPhotoAdapter = ContentAdapter(ContentType.PHOTO, clickListener)
        binding.rvAllPhotoPhoto.adapter = allPhotoAdapter
        allPhotoAdapter.submitList(args.photoItems.toList())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}