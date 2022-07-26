package com.realworld.io.presentation.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.Repositoryimpl
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.LoginInput
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: Repositoryimpl, private val roomRepository: RoomRepository) : ViewModel() {

    private val _articleState = MutableStateFlow<Resource<Article>>(Resource.Loading())
    val articleUIState: StateFlow<Resource<Article>> = _articleState

    private val _offlineArticleState = MutableStateFlow<Resource<MutableList<ArticleX>>>(Resource.Loading())
    val offlineArticleUIState: StateFlow<Resource<MutableList<ArticleX>>> = _offlineArticleState

    fun fetchAllArticle() = viewModelScope.launch {
        _articleState.value = Resource.Loading()
        val response = articleRepository.getArticle()
        if (response.isSuccessful) {
            roomRepository.insertRecords(response.body()!!.articles)
            _articleState.value =Resource.Success(response.body()!!)
        } else {
            _articleState.value = Resource.Error("No Data Found")
        }
    }

    fun fetchOfflineArticle()=  viewModelScope.launch{
        _offlineArticleState.value = Resource.Loading()
        val response =  roomRepository.getRecords()
        if (!response.isNullOrEmpty()) {
            _offlineArticleState.value =Resource.Success(response)
        } else {
            _offlineArticleState.value = Resource.Error("No Data Found")
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