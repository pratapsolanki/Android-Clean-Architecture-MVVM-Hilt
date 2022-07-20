package com.example.dagger_hilt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.realworld.io.model.ArticleModel

@Dao
interface AppDao {
    @Query("SELECT * FROM article_table")
    fun getArticle(): List<ArticleModel>

    @Insert
    fun insertArticle(articleModel: ArticleModel)
}