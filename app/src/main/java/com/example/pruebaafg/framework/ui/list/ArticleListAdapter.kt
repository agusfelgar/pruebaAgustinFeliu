package com.example.pruebaafg.framework.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebaafg.R
import com.example.pruebaafg.data.model.Article
import com.example.pruebaafg.databinding.ArticleListItemBinding
import com.squareup.picasso.Picasso


class ArticleListAdapter(val articleList : List<Article>, private val itemClickListener : (Article) -> Unit) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList.get(position)
        holder.bind(article, itemClickListener)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ArticleListItemBinding

        fun bind(article: Article, itemClickListener: (Article) -> Unit) {
            binding = ArticleListItemBinding.bind(itemView)

            itemView.setOnClickListener { itemClickListener(article) }

            binding.tvTitle.text = article.title
            binding.tvSubtitle.text = article.autor
            binding.tvSection.text = article.section
            binding.tvDate.text = article.date
            if(getArticleImage(article).isEmpty()) {
                Picasso.get().load(R.drawable.ic_error_image)
                    .fit()
                    .placeholder(R.drawable.ic_loading_image)
                    .error(R.drawable.ic_error_image)
                    .into(binding.ivArticle)
            }
            else {
                Picasso.get().load(article.media[0].mediaMetaData.get(0).imageUrl)
                    .fit()
                    .placeholder(R.drawable.ic_loading_image)
                    .error(R.drawable.ic_error_image)
                    .into(binding.ivArticle)
            }
        }

        fun getArticleImage(article: Article): String {
            var imageUrl: String = ""
            val mediaInfoList = article.media ?: listOf()
            if (mediaInfoList.size > 0) {
                val position = 0
                do {
                    var mediaDataList = mediaInfoList.get(position).mediaMetaData
                    if (mediaDataList.size > 0) {
                        val mdposition = 0;
                        do {
                            if (!mediaDataList.get(mdposition).imageUrl.isNullOrEmpty()) {
                                imageUrl = mediaDataList.get(mdposition).imageUrl
                            }
                        } while (mdposition <= mediaDataList.size && imageUrl.isEmpty())
                    }
                } while (position <= mediaInfoList.size && imageUrl.isEmpty())

            }
            return imageUrl
        }
    }
}