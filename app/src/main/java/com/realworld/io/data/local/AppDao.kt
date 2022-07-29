package com.realworld.io.data.local

import androidx.room.*
import com.realworld.io.domain.model.ArticleX

/**
 * App Dao for access your application’s persisted data.
 * A DAO can be either an interface or an abstract class
 */
@Dao
interface AppDao {

    /**
     * Insert Query article_table
     */
    @Query("SELECT * FROM article_table")
    suspend fun getArticle(): MutableList<ArticleX>

    /**
     * Insert List  Query article_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListArticle(article: List<ArticleX>)

    /**
     * Insert Single Query article_table
     */
    @Insert
    suspend fun insertSingleArticle(article: ArticleX) : Long

    /**
     * Delete Query article_table
     */
    @Delete
    suspend fun deleteArticle(article: ArticleX)

    /**
     * Update Query article_table
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateArticle(article: ArticleX)

}