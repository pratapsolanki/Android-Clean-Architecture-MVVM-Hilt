package com.realworld.io.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realworld.io.databinding.SingleArticleBinding
import com.realworld.io.model.ArticleModel


class ArticleAdapter(private var articleModel: List<ArticleModel> , private val listener: OnItemClickListener) : RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleArticleBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val article = articleModel[position]
        holder.binding.titleText.text = article.title

        holder.itemView.setOnClickListener {
            listener.itemClick(it,position,article)
        }

        holder.binding.changeBtn.setOnClickListener {
            listener.btnClick(it,position,article)
        }

    }

    override fun getItemCount(): Int {
        return articleModel.size
    }

    interface OnItemClickListener{
        fun itemClick(view: View, position: Int, article: ArticleModel)
        fun btnClick(view: View, position: Int, article: ArticleModel)
    }


    fun setData(articleModel : List<ArticleModel>){
        this.articleModel = articleModel
        notifyDataSetChanged()
    }

}

class MainViewHolder(var binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.root)
