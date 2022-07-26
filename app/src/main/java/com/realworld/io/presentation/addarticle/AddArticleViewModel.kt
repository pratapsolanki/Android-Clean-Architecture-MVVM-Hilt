package com.realworld.io.presentation.addarticle

import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.UserLoginResponse
import com.realworld.io.util.Logger
import com.realworld.io.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddArticleViewModel  @Inject constructor(private val roomRepository: RoomRepository) : ViewModel() {

    fun addArticle(articleModel: ArticleX){
            CoroutineScope(Dispatchers.IO).launch {
                val response = roomRepository.insertSingleArticle(articleModel)
            }
        }

}