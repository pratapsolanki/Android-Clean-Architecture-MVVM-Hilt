package com.realworld.io.presentation.editarticle

import androidx.lifecycle.ViewModel
import com.realworld.io.data.repo.RoomRepository
import com.realworld.io.domain.model.ArticleModel
import com.realworld.io.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditArticleViewModel @Inject constructor(var roomRepository: RoomRepository) : ViewModel() {

    fun updateArticle(articleModel: ArticleModel){
        CoroutineScope(Dispatchers.IO).launch {
           val result=  roomRepository.updateArticle(articleModel)
            Logger.d("$result  result")
        }
    }
}