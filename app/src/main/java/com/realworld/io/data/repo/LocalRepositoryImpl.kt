package com.realworld.io.data.repo

import com.realworld.io.data.local.AppDao
import com.realworld.io.domain.model.ArticleX
import javax.inject.Inject

/**
 * Local Repository class implementation
 * This class will communication with view Model , Activity not DAO
 */

class LocalRepositoryImpl @Inject constructor(private val appDao: AppDao) {

    /**
     * Get record from database via DAO
     */
    suspend fun getRecords(): MutableList<ArticleX>{
        return appDao.getArticle()
    }

    /**
     * Get record from database via DAO
     */
    suspend fun insertSingleArticle(article: ArticleX) : Long{
        return appDao.insertSingleArticle(article)
    }

    /**
     * Insert list of Recode in database via DAO
     */
    suspend fun insertRecords(article: List<ArticleX>) {
        appDao.insertListArticle(article)
    }

    /**
     * Delete record in database via DAO
     */
    suspend fun deleteArticle(article: ArticleX){
        appDao.deleteArticle(article)
    }

    /**
     * Update record in database via DAO
     */
    suspend fun updateArticle(article: ArticleX) {
        appDao.updateArticle(article)
    }

}