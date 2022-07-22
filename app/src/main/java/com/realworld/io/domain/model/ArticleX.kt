package com.realworld.io.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article_table")
data class ArticleX(
    val author: Author = Author(),
    val body: String ="",
    val createdAt: String ="",
    val description: String ="",
    val favorited: Boolean = false,
    val favoritesCount: Int = 0,
    val slug: String ="",
    val tagList: List<String>,
    @PrimaryKey
    val title: String ="",
    val updatedAt: String =""
): Parcelable