package com.realworld.io.presentation.addarticle

import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.ArticleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddArticleViewModel  @Inject constructor(val roomRepository: RoomRepository) : ViewModel() {

    fun addArticle(articleModel: ArticleModel){
            CoroutineScope(Dispatchers.IO).launch {
                val response = roomRepository.insertRecords(articleModel)
            }
        }

}