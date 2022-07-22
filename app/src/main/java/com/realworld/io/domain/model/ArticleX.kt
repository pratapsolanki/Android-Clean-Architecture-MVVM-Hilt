package com.realworld.io.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleX(
    val author: Author,
    val body: String ="",
    val createdAt: String ="",
    val description: String ="",
    val favorited: Boolean,
    val favoritesCount: Int,
    val slug: String ="",
    val tagList: List<String>,
    val title: String ="",
    val updatedAt: String =""
): Parcelable