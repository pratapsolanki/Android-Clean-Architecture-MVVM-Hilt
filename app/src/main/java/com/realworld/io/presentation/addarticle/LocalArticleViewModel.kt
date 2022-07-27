package com.realworld.io.presentation.addarticle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.LocalRepositoryImpl
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * This view model will responsible of all the local operation
 */

@HiltViewModel
class LocalArticleViewModel  @Inject constructor(private val dispatcherImpl: DispatcherProviders,
                                                 private val roomRepository: LocalRepositoryImpl) : ViewModel() {

    /**
     * private mutable stateflow for accessing and updating data
     */

    private val _offlineArticleState = MutableStateFlow<Resource<MutableList<ArticleX>>>(Resource.Loading())

    /**
     * public  stateflow for accessing and updating data from other class
     */

    val offlineArticleUIState: StateFlow<Resource<MutableList<ArticleX>>> = _offlineArticleState

    /**
     * This function is use to add  single Article in local database
     */

    fun addArticle(articleModel: ArticleX){
            CoroutineScope(dispatcherImpl.io).launch {
               roomRepository.insertSingleArticle(articleModel)
            }
    }

    /**
     * This function is use to fetch for all the Article list from local database
     */

    fun fetchOfflineArticle()=  viewModelScope.launch{
        _offlineArticleState.value = Resource.Loading()
        val response =  roomRepository.getRecords()

        if (response.isNotEmpty()) {
            _offlineArticleState.value = Resource.Success(response)
        } else {
            _offlineArticleState.value = Resource.Error("No Data Found")
        }
    }

    /**
     * This function is use to delete single the Article in local database
     */

    fun deleteArticle(article: ArticleX){
        CoroutineScope(dispatcherImpl.io).launch {
            roomRepository.deleteArticle(article)
            fetchOfflineArticle()
        }
    }

    /**
     * This function is use to Update Single Article in local database
     */

    fun updateArticle(article: ArticleX){
        CoroutineScope(dispatcherImpl.io).launch {
            roomRepository.updateArticle(article)
            fetchOfflineArticle()
        }
    }

}