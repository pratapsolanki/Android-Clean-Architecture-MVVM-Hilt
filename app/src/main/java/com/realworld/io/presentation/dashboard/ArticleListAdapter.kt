package com.realworld.io.presentation.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.realworld.io.databinding.SingleArticleBinding
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.loadImage

/**
 * Article adapter For showing recyclerview Data
 */
class ArticleAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<MainViewHolder>() ,Filterable{

    //original list
    private  var articleList: ArrayList<ArticleX> = ArrayList()
    //Filter list
    private  var articleFilterList: ArrayList<ArticleX>  = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleArticleBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val article = articleFilterList[position]

        holder.binding.shortDesc.text = article.body
        holder.binding.title.text = article.title
        holder.binding.userName.text = article.author.username
        holder.itemView.context.loadImage(holder.binding.userIcon, article.author.image)

        //Item click
        holder.itemView.setOnClickListener {
            listener.itemClick(it, position, article)
        }

        //Item Long click
        holder.itemView.setOnLongClickListener {
            listener.itemClickLong(it, position, article)
            false
        }

        //Button click
        holder.binding.changeBtn.setOnClickListener {
            listener.btnClick(it, position, article)
        }

    }

    //return list size
    override fun getItemCount(): Int {
        return articleFilterList.size
    }

    //Register Interface click
    interface OnItemClickListener {
        fun itemClick(view: View, position: Int, article: ArticleX)
        fun btnClick(view: View, position: Int, article: ArticleX)
        fun itemClickLong(view: View, position: Int, article: ArticleX)
    }


    //SetData and initialize list
    fun setData(articleModel: List<ArticleX>) {
       articleList = articleModel as ArrayList<ArticleX>
       articleFilterList = articleList
        notifyDataSetChanged()
    }

    //Filter function
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.trim().isEmpty()) {
                    articleFilterList = articleList
                } else {
                    val filteredList = ArrayList<ArticleX>()
                    articleList
                        .filter {
                            (it.title.contains(constraint!!)) or
                                    (it.description.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    articleFilterList = filteredList

                }
                return FilterResults().apply { values = articleFilterList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                articleFilterList = if (results?.values == null || results.values == "")
                    ArrayList()
                else
                    results.values as ArrayList<ArticleX>
                notifyDataSetChanged()


            }
        }
    }
}
class MainViewHolder(var binding: SingleArticleBinding) : RecyclerView.ViewHolder(binding.root)
