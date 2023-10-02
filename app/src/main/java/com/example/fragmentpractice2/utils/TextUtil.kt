package com.example.fragmentpractice2.utils

object TextUtil {

    fun getHighResPhoto(midResArtworkUri: String) =
        midResArtworkUri.replaceAfterLast('/', "300x300bb.jpg")
}