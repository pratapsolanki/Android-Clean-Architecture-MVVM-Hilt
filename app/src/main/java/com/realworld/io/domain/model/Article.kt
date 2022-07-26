package com.realworld.io.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val articles: MutableList<ArticleX>,
    val articlesCount: Int
) : Parcelable