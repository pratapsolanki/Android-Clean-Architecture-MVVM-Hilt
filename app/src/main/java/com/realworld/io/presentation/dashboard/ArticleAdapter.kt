package com.realworld.io.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.realworld.io.R
import com.realworld.io.databinding.SingleArticleBinding
import com.realworld.io.model.ArticleModel
import javax.inject.Inject

class ArticleAdapter @Inject constructor() : RecyclerView.Adapter<MainViewHolder>() {

    var articleList = mutableListOf<ArticleModel>()
    lateinit var onClickItemListerner : OnClickItemListerner
    fun setArticle(article: List<ArticleModel>) {
        this.articleList = article as MutableList<ArticleModel>
        notifyDataSetChanged()
    }


    fun registerInterface(onClickItemListerner: OnClickItemListerner){
        this.onClickItemListerner = onClickItemListerner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleArticleBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int ) {
        val article = articleList[position]
        holder.binding.titleText.text = article.title
        holder.itemView.setOnClickListener {
        }

        holder.binding.topAppBar.setOnClickListener {
            onClickItemListerner.item(position,article)
        }

    }



    interface OnClickItemListerner{
        fun item(position: Int,model: ArticleModel)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}

class MainViewHolder(val binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.root)

