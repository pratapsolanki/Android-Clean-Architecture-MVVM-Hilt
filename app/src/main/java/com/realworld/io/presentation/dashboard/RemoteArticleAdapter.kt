package com.realworld.io.presentation.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realworld.io.databinding.SingleArticleBinding
import com.realworld.io.model.Article
import com.realworld.io.model.ArticleModel
import com.realworld.io.model.ArticleX
import com.realworld.io.util.Logger

class RemoteArticleAdapter(private var articleModel: List<ArticleX>, private val listener: OnItemClickListener) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleArticleBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val article = articleModel[position]
        holder.binding.shortDesc.text = article.body
        holder.binding.title.text = article.title
        holder.binding.userName.text = article.author.username
        holder.itemView.setOnClickListener {
            listener.itemClick(it,position,article)
        }

        holder.itemView.setOnLongClickListener {
            Logger.d("Longpressed")
            listener.itemClickLong(it,position,article)
            false
        }
        holder.binding.changeBtn.setOnClickListener {
            listener.btnClick(it,position,article)
        }

    }

    override fun getItemCount(): Int {
        return articleModel.size
    }

    interface OnItemClickListener{
        fun itemClick(view: View, position: Int, article: ArticleX)
        fun btnClick(view: View, position: Int, article: ArticleX)
        fun itemClickLong(view: View, position: Int, article: ArticleX)
    }


    fun setData(articleModel: Article){
        this.articleModel = articleModel
        notifyDataSetChanged()
    }

}


class MainViewHolder(var binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.root)