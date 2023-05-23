package kg.kunduznbkva.newsapp.utils.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kg.kunduznbkva.newsapp.databinding.NewsItemBinding
import kg.kunduznbkva.newsapp.model.Article
import kg.kunduznbkva.newsapp.utils.loadImage

class NewsAdapter : RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

}

class NewsViewHolder(private var binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(article: Article?) {
        article?.let {
            article.urlToImage?.let { it1 -> binding.newsImage.loadImage(it1) }
            binding.newsTitle.text = article.title
            binding.newsAuthor.text = article.author
            binding.newsTime.text = article.publishedAt
            binding.newsDescription.text = article.description
            setOnItemClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener:(Article)-> Unit){
        onItemClickListener = listener
    }

}
