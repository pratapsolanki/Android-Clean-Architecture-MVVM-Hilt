package com.realworld.io.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.ArrayList

@Parcelize
data class Article(
    val articles: MutableList<ArticleX>,
    val articlesCount: Int
) : Parcelable