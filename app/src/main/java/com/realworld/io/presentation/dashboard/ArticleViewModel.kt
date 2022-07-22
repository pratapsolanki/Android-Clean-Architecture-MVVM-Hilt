package com.realworld.io.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realworld.io.util.Resource
import com.realworld.io.domain.model.Article
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.ArticleModel
import com.realworld.io.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.lang.Exception

@HiltViewModel
class ArticleViewModel @Inject constructor(val articalRepository: Repositoryimpl , val roomRepository: RoomRepository) : ViewModel() {
    val articleList = MutableLiveData<Resource<Article>>()
    val offlineArticleList = MutableLiveData<Resource<List<ArticleModel>>>()

    fun fetchOfflineArticle(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                offlineArticleList.postValue(Resource.Loading())
                val response = roomRepository.getRecords()
                if (!response.isNullOrEmpty()) {
                    offlineArticleList.postValue(Resource.Success(response))
                    Logger.d("$offlineArticleList")
                }else{
                    offlineArticleList.postValue(Resource.Error("Empty List"))
                    Logger.d("$offlineArticleList")

                }
            }
        }catch (e: Exception){
            offlineArticleList.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun fetchAllArticle() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                articleList.postValue(Resource.Loading())
                val response = articalRepository.getArticle()
                if (response.isSuccessful) {
                    articleList.postValue(Resource.Success(response.body()!!))
                }else{
                    articleList.postValue(Resource.Error("API ERROR or SERVER IS DOWN"))
                }
            }
        }catch (e: Exception){
            articleList.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun deleteArticle(articleModel: ArticleModel){
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.deleteArticle(articleModel)
            fetchOfflineArticle()
        }
    }

}