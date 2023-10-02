package com.example.fragmentpractice2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.fragmentpractice2.R
import com.example.fragmentpractice2.databinding.FragmentUserEditBinding
import com.example.fragmentpractice2.domain.User
import com.example.fragmentpractice2.utils.TextUtil

class UserEditFragment : Fragment() {

    private lateinit var binding: FragmentUserEditBinding
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(USER, User::class.java)
        } else {
            arguments?.getParcelable(USER)
        }
        renderUi(user!!)

        binding.saveButton.setOnClickListener {
            if (user != null) {
                val updatedUser = user!!.copy(
                    photoUri = user!!.photoUri,
                    name = binding.userName.text.toString(),
                    secondName = binding.userSecondName.text.toString(),
                    phoneNumber = binding.userPhoneNumber.text.toString().toLong()
                )
            }
        }

    }

    private fun renderUi(user: User) {
        binding.userName.setText(user.name)
        binding.userSecondName.setText(user.secondName)
        binding.userPhoneNumber.setText(user.phoneNumber.toString())
        Glide.with(requireContext())
            .load(TextUtil.getHighResPhoto(user.photoUri))
            .transform(
                CenterCrop(),
                RoundedCorners(
                    requireContext().resources
                        .getDimensionPixelSize(R.dimen.rounded_corners_radius)
                )
            )
            .into(binding.photo)

    }

    companion object {
        const val USER = "USER"
    }
}