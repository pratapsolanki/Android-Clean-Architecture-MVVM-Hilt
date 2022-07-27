package com.realworld.io.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.RemoteRepositoryImpl
import com.realworld.io.data.repo.LocalRepositoryImpl
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.Logger
import com.realworld.io.util.Resource
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val articleRepository: RemoteRepositoryImpl,
                                             private val roomRepository: LocalRepositoryImpl) : ViewModel() {

    private val _articleState = MutableStateFlow<Resource<Article>>(Resource.Loading())
    val articleUIState: StateFlow<Resource<Article>> = _articleState

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

}
