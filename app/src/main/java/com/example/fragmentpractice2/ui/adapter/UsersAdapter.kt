package com.example.fragmentpractice2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentpractice2.R
import com.example.fragmentpractice2.domain.User

class UsersAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<UsersViewHolder>() {

    var userList: ArrayList<User> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder =
        UsersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_container, parent, false)
        )

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.onBind(userList[position])
        holder.itemView.setOnClickListener { clickListener.onClick(userList[position]) }
    }

    override fun getItemCount(): Int = userList.size

    fun interface ClickListener {
        fun onClick(user: User)
    }
}