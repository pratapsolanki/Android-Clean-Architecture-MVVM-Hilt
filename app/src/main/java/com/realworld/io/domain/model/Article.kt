package com.realworld.io.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val articles: MutableList<ArticleX>,
    val articlesCount: Int
) : Parcelable