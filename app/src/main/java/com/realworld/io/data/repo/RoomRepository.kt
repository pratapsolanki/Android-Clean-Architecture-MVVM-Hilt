package com.realworld.io.data.repo

import com.example.dagger_hilt.dao.AppDao
import com.realworld.io.model.ArticleModel
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDao: AppDao) {

    fun getRecords():List<ArticleModel>{
        return appDao.getArticle()
    }

    fun insertRecords(article: ArticleModel) {
        appDao.insertArticle(article)
    }
}