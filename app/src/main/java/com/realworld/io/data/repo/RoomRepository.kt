package com.realworld.io.data.repo

import com.realworld.io.data.local.AppDao
import com.realworld.io.domain.model.ArticleX
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    suspend fun getRecords(): MutableList<ArticleX>{
        return appDao.getArticle()
    }

    suspend fun insertSingleArticle(article: ArticleX) : Long{
        return appDao.insertSingleArticle(article)
    }

    suspend fun insertRecords(article: List<ArticleX>) {
        appDao.insertListArticle(article)
    }

    suspend fun deleteArticle(article: ArticleX){
        appDao.deleteArticle(article)
    }

    suspend fun updateArticle(article: ArticleX) {
        appDao.updateArticle(article)
    }

}