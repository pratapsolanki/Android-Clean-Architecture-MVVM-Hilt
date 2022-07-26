package com.realworld.io.data.repo

import com.realworld.io.data.local.AppDao
import com.realworld.io.domain.model.ArticleX
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    fun getRecords(): MutableList<ArticleX>{
        return appDao.getArticle()
    }

    fun insertSingleArticle(article: ArticleX) : Long{
        return appDao.insertSingleArticle(article)
    }

    fun insertRecords(article: List<ArticleX>) {
        appDao.insertListArticle(article)
    }

    fun deleteArticle(article: ArticleX){
        appDao.deleteArticle(article)
    }

    fun updateArticle(article: ArticleX) {
        appDao.updateArticle(article)
    }

}