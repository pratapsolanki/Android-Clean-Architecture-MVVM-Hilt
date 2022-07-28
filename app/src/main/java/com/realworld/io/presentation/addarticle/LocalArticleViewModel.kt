package com.realworld.io.presentation.addarticle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realworld.io.data.dispatcher.DispatcherProviders
import com.realworld.io.data.repo.LocalRepositoryImpl
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.RegistrationFormState
import com.realworld.io.domain.use_cases.ValidateDesc
import com.realworld.io.domain.use_cases.ValidateShortDesc
import com.realworld.io.domain.use_cases.ValidateTitle
import com.realworld.io.util.Logger
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
class LocalArticleViewModel  @Inject constructor(
    private val dispatcherImpl: DispatcherProviders,
    private val roomRepository: LocalRepositoryImpl,
    private val validateTitle: ValidateTitle,
    private val validateDesc: ValidateDesc,
    private val validateShortDesc: ValidateShortDesc) : ViewModel() {

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

    var state by mutableStateOf(RegistrationFormState())

    fun addArticle(articleModel: ArticleX){
        val titleResult = validateTitle.execute(articleModel.title)
        val shortDescResult = validateShortDesc.execute(articleModel.body)
        val descriptionResult = validateDesc.execute(articleModel.description)


        val hasError = listOf(
            titleResult,
            shortDescResult,
            descriptionResult
        ).any{
            !it.successful
        }
        state = state.copy(
            titleError = titleResult.errorMessage,
            shortDescError = shortDescResult.errorMessage,
            descriptionError = descriptionResult.errorMessage,
        )
        if(hasError) { return }

        CoroutineScope(dispatcherImpl.io).launch {
               roomRepository.insertSingleArticle(articleModel)
                fetchOfflineArticle()
        }
    }

    /**
     * This function is use to fetch for all the Article list from local database
     */

    fun fetchOfflineArticle() =  viewModelScope.launch{
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