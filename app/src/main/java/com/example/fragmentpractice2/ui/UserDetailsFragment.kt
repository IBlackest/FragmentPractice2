package com.example.fragmentpractice2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.fragmentpractice2.R
import com.example.fragmentpractice2.databinding.FragmentUserDetailsBinding
import com.example.fragmentpractice2.domain.User
import com.example.fragmentpractice2.utils.TextUtil

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(USER, User::class.java)
        } else {
            arguments?.getParcelable(USER)
        }
        if (user != null) renderUi(user!!)

        binding.editUserButton.setOnClickListener {
            if (user != null) {
                parentFragmentManager.commit {
                    addToBackStack(null)
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container, getUserEditFragment(user!!))
                }
            }
        }
    }

    private fun getUserEditFragment(user: User) = UserEditFragment().apply {
        arguments = bundleOf(UsersFragment.USER to user)
    }

    private fun renderUi(user: User) {
        binding.userName.text = user.name
        binding.userSecondName.text = user.secondName
        binding.userPhoneNumber.text = user.phoneNumber.toString()
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