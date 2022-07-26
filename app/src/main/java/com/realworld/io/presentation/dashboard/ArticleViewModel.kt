package com.realworld.io.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articalRepository: Repositoryimpl, private val roomRepository: RoomRepository) : ViewModel() {
    val articleList = MutableLiveData<Resource<Article>>()
    val offlineArticleList = MutableLiveData<List<ArticleX>>()
    fun fetchOfflineArticle(){
            CoroutineScope(Dispatchers.IO).launch{
                    val data = roomRepository.getRecords()
                    offlineArticleList.postValue(data)
            }
    }

    fun fetchAllArticle() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                articleList.postValue(Resource.Loading())
                val response = articalRepository.getArticle()
                if (response.isSuccessful) {
                    articleList.postValue(Resource.Success(response.body()!!))
                    roomRepository.insertRecords(response.body()!!.articles)
                }else{
                    articleList.postValue(Resource.Error("API ERROR or SERVER IS DOWN"))
                }
            }
        }catch (e: Exception){
            articleList.postValue(Resource.Error(e.message.toString()))
        }
    }


    fun deleteArticle(article: ArticleX){
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.deleteArticle(article)
            fetchOfflineArticle()
        }
    }

    fun updateArticle(article: ArticleX){
        CoroutineScope(Dispatchers.IO).launch {
            roomRepository.updateArticle(article)
            fetchOfflineArticle()
        }
    }


}