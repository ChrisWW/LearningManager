package com.example.learningmanager.fragments.myinspiration.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.learningmanager.MainActivity
import com.example.learningmanager.R
import com.example.learningmanager.base.ui.BaseFragment
import com.example.learningmanager.databinding.FragmentMyInspirationSaveBinding
import com.example.learningmanager.fragments.myinspiration.FirebaseManager
import com.example.learningmanager.fragments.myinspiration.data.MyInspirationDetailsResponse
import com.example.learningmanager.fragments.myinspiration.domain.AddImageMyInspirationFirebaseStorageUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MyInspirationSaveFragment @Inject constructor() :
    BaseFragment<FragmentMyInspirationSaveBinding, MyInspirationViewModel>(
        FragmentMyInspirationSaveBinding::inflate
    ) {
    override val vm: MyInspirationViewModel by activityViewModels()
    private val currentData = SimpleDateFormat.getInstance().format(Date())
    var imgURI: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        Glide.with(requireView()).load(R.drawable.gif_clickme).into(layout.ivAddImage)
        addPicture()
        acceptPicture()
        addInspiration()
    }

    private fun addPicture() {
        layout.ivAddImage.setOnClickListener {
            MainActivity.addPictureResultCode = true
            vm.addPicture(requireActivity())
        }
    }

    private fun acceptPicture() {
        layout.acceptPicture.setOnClickListener {
            lifecycleScope.launch {
                vm.acceptPicture(imgURI!!, requireActivity())
            }
//            onRefreshFromMainActivity()
//            FirebaseManager().uploadImage(imgURI!!)
        }
    }

    private fun addInspiration() {
        layout.saveInspiration.setOnClickListener {
            vm.saveInspiration(
                MyInspirationDetailsResponse(
                    0,
                    layout.etInspiration.text.toString(),
                    layout.etInspirationContent.text.toString(),
                    "layout.ivAddImage.",
                    currentData.toString(),
                    "",
                    ""
                ),
                requireActivity()
            )
        }

    }

    private fun onRefreshFromMainActivity() {
        if (MainActivity.inspirationImage != "") {
            imgURI = Uri.parse(MainActivity.inspirationImage)
            layout.ivAddImage.setImageURI(imgURI)
//            binding.addImage.tag = uri
//            FirebaseManager().uploadImage(requireContext(), imgURI!!)
        }
    }

    override fun onResume() {
        super.onResume()
        onRefreshFromMainActivity()
    }
}