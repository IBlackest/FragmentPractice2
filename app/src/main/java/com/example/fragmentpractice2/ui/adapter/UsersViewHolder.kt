package com.example.fragmentpractice2.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.fragmentpractice2.R
import com.example.fragmentpractice2.domain.User

class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val photo: ImageView = itemView.findViewById(R.id.photo)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val secondName: TextView = itemView.findViewById(R.id.second_name)
    private val phoneNumber: TextView = itemView.findViewById(R.id.phone_number)

    fun onBind(user: User) {
        Glide.with(itemView)
            .load(user.photoUri)
            .transform(
                CenterCrop(),
                RoundedCorners(
                    itemView.resources
                        .getDimensionPixelSize(R.dimen.rounded_corners_radius)
                )
            )
            .into(photo)
        name.text = user.name
        secondName.text = user.secondName
        phoneNumber.text = if (user.phoneNumber == 0L) "" else user.phoneNumber.toString()
    }
}