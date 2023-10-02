package com.example.fragmentpractice2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragmentpractice2.R
import com.example.fragmentpractice2.data.DataProvider
import com.example.fragmentpractice2.databinding.FragmentUsersBinding
import com.example.fragmentpractice2.domain.User
import com.example.fragmentpractice2.ui.adapter.UsersAdapter

class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usersAdapter = UsersAdapter { user ->
            parentFragmentManager.commit {
                addToBackStack(null)
                setReorderingAllowed(true)
                replace(R.id.fragment_container, getUserDetailsFragment(user))
            }
        }
        usersAdapter.userList = DataProvider.usersList

        binding.recyclerView.apply {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getUserDetailsFragment(user: User) = UserDetailsFragment().apply {
        arguments = bundleOf(USER to user)
    }

    companion object {
        const val USER = "USER"
    }
}