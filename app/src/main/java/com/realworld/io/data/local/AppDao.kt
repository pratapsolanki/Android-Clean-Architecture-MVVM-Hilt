package com.realworld.io.data.local

import androidx.room.*
import com.realworld.io.domain.model.ArticleX

@Dao
interface AppDao {

    @Query("SELECT * FROM article_table")
    suspend fun getArticle(): MutableList<ArticleX>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListArticle(article: List<ArticleX>)

    @Insert
    suspend fun insertSingleArticle(article: ArticleX) : Long

    @Delete
    suspend fun deleteArticle(article: ArticleX)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateArticle(article: ArticleX)

}