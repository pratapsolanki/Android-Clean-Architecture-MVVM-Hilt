package com.realworld.io.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_table")
data class ArticleModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String ="",
    val title: String ="",
    val body: String ="",
    val description: String ="",
    val bio: String =" ",
    val slug: String ="",
    val image: String ="",
    val favoritesCount: Int = 0,
    val favorited: Boolean = false,
    val following: Boolean = false,
    val createdAt: String ="",
    val updatedAt: String ="",
)