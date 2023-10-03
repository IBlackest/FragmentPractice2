package com.example.fragmentpractice2.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val photoUri: String,
    val name: String,
    val secondName: String,
    val phoneNumber: Long
) : Parcelable

