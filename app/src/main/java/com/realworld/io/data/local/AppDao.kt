package com.example.dagger_hilt.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*
import com.realworld.io.domain.model.ArticleModel

@Dao
interface AppDao {
    @Query("SELECT * FROM article_table")
    fun getArticle(): List<ArticleModel>

    @Insert
    fun insertArticle(articleModel: ArticleModel)

    @Delete
    fun delete(articleModel: ArticleModel) : Int

    @Update
    fun update(articleModel: ArticleModel): Int
}