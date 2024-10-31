package com.example.mute.ui.add

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mute.databinding.FragmentAddFileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFileFragment : Fragment() {

    private var _binding: FragmentAddFileBinding? = null
    private val binding get() = _binding!!

    private val galleryImagePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openGalleryImage()
        } else {
            Toast.makeText(requireContext(), "갤러리 이미지 권한이 허용되어 있지않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private val galleryVideoPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openGalleryVideo()
        } else {
            Toast.makeText(requireContext(), "갤러리 동영상 권한이 허용되어 있지않습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFileBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListener()
        setActivityLauncher()
    }

    private fun setListener() {
        binding.btnAddFileImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                galleryImagePermissionLauncher.launch(readImagePermission)
            } else {
                galleryImagePermissionLauncher.launch(readExternalPermission)
            }
        }

        binding.btnAddFileVideo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                galleryVideoPermissionLauncher.launch(readVideoPermission)
            } else {
                galleryVideoPermissionLauncher.launch(readExternalPermission)
            }
        }
    }

    private fun setActivityLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == RESULT_OK) {
                    val data = result.data
                    data?.data?.let { uri ->
                        requireContext().contentResolver.getType(uri)?.let { mimeType ->
                            val fileType = when {
                                mimeType.startsWith("image") -> "image"
                                mimeType.startsWith("video") -> "video"
                                else -> null
                            }
                            if (fileType != null) {
                                val absolutePath = getAbsolutePath(uri)
                                if (fileType == "image") {
                                    val action =
                                        AddFileFragmentDirections.actionAddFileFragmentToAddImageFragment(
                                            absolutePath
                                        )
                                    findNavController().navigate(action)
                                } else {
                                    val action =
                                        AddFileFragmentDirections.actionAddFileFragmentToAddVideoFragment(
                                            absolutePath
                                        )
                                    findNavController().navigate(action)

                                }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "지원하지 않는 파일입니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } ?: {
                            Toast.makeText(requireContext(), "파일을 선택해주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "파일을 선택해주세요", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun openGalleryImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        activityResultLauncher.launch(intent)
    }

    private fun openGalleryVideo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*")
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT

        activityResultLauncher.launch(intent)
    }

    private fun getAbsolutePath(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        return if (cursor == null) {
            uri.path!!
        } else {
            cursor.moveToFirst()
            val index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            val path = cursor.getString(index)
            cursor.close()
            path
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val readExternalPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        private const val readImagePermission = android.Manifest.permission.READ_MEDIA_IMAGES
        private const val readVideoPermission = android.Manifest.permission.READ_MEDIA_VIDEO
    }
}