package com.realworld.io.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.*
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX

@Dao
interface AppDao {

    @Query("SELECT * FROM article_table")
    fun getArticle(): List<ArticleX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListArticle(article: List<ArticleX>)

    @Insert
    fun insertSingleArticle(article: ArticleX)

    @Delete
    fun deleteArticle(article: ArticleX)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArticle(article: ArticleX)

}