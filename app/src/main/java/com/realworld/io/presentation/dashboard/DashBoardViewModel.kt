package com.realworld.io.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.repo.LocalRepositoryImpl
import com.realworld.io.data.repo.RemoteRepositoryImpl
import com.realworld.io.domain.model.Article
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(private val articleRepository: RemoteRepositoryImpl,
                                             private val roomRepository: LocalRepositoryImpl) : ViewModel() {


    private val _articleState = MutableStateFlow<Resource<Article>>(Resource.Loading())
    val articleUIState = _articleState.asStateFlow()

     fun fetchAllArticle() = viewModelScope.launch {
        val response = articleRepository.getArticle()
        _articleState.emit(Resource.Loading())
        if (response.isSuccessful) {
            roomRepository.insertRecords(response.body()!!.articles)
            _articleState.emit(Resource.Success(response.body()!!))
        } else {
            _articleState.emit(Resource.Error("No Data Found"))
        }
    }




}
