package com.realworld.io.domain.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val bio: String =" ",
    val following: Boolean,
    val image: String ="",
    val username: String =""
) : Parcelable