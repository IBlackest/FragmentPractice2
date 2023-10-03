package com.example.fragmentpractice2.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
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
    private var updatedUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.apply {
                    popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    commit {
                        setReorderingAllowed(true)
                        replace(R.id.fragment_container, UsersFragment())
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(USER, User::class.java)
        } else {
            arguments?.getParcelable(USER)
        }
        if (user != null) renderUi(user!!)

        setFragmentResultListener(REQUEST) { _, bundle ->
            updatedUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(BUNDLE, User::class.java)
            } else {
                bundle.getParcelable(BUNDLE)
            }
            if (updatedUser != null) renderUi(updatedUser!!)
        }

        binding.editUserButton.setOnClickListener {
            if (user != null) {
                parentFragmentManager.commit {
                    addToBackStack(null)
                    setReorderingAllowed(true)
                    replace(R.id.fragment_container, getUserEditFragment(user!!))
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun getUserEditFragment(user: User) = UserEditFragment().apply {
        arguments = bundleOf(UsersFragment.USER to user)
    }

    private fun renderUi(user: User) {
        binding.apply {
            userName.text = user.name
            userSecondName.text = user.secondName
            userPhoneNumber.text =
                if (user.phoneNumber == 0L) "" else user.phoneNumber.toString()
        }
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
        const val REQUEST = "REQUEST"
        const val BUNDLE = "BUNDLE"
        const val USER = "USER"
    }
}