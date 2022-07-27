package com.realworld.io.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.RepositoryImpl
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.Logger
import com.realworld.io.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val dispatcherImpl: DispatcherProviders, private val articleRepository: RepositoryImpl, private val roomRepository: RoomRepository) : ViewModel() {

    private val _articleState = MutableStateFlow<Resource<Article>>(Resource.Loading())
    val articleUIState: StateFlow<Resource<Article>> = _articleState

    private val _offlineArticleState = MutableStateFlow<Resource<MutableList<ArticleX>>>(Resource.Loading())
    val offlineArticleUIState: StateFlow<Resource<MutableList<ArticleX>>> = _offlineArticleState

    fun fetchAllArticle() = viewModelScope.launch {
        _articleState.value = Resource.Loading()
        Logger.d(articleUIState.value.toString() + "Loading")

        val response = articleRepository.getArticle()
        if (response.isSuccessful) {
            roomRepository.insertRecords(response.body()!!.articles)
            _articleState.value =Resource.Success(response.body()!!)
            Logger.d(response.body().toString() + " Success")
        } else {
            _articleState.value = Resource.Error("No Data Found")
        }
    }

    fun fetchOfflineArticle()=  viewModelScope.launch{
        _offlineArticleState.value = Resource.Loading()
        val response =  roomRepository.getRecords()

        if (response.isNotEmpty()) {
            _offlineArticleState.value =Resource.Success(response)
        } else {
            _offlineArticleState.value = Resource.Error("No Data Found")
        }
    }


    fun deleteArticle(article: ArticleX){
        CoroutineScope(dispatcherImpl.io).launch {
            roomRepository.deleteArticle(article)
            fetchOfflineArticle()
        }
    }

    fun updateArticle(article: ArticleX){
        CoroutineScope(dispatcherImpl.io).launch {
            roomRepository.updateArticle(article)
            fetchOfflineArticle()
        }
    }


}
