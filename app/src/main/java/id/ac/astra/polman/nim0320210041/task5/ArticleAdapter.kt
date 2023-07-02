package id.ac.astra.polman.nim0320210041.task5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ArticleAdapter(var articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.textTitle.text = article.title


        if (article.imageUrl != null) {
            Picasso.get()
                .load(article.imageUrl)
                .fit()
                .centerCrop()
                .into(holder.imageThumbnail)
        } else {
            val defaultImageUrl = R.drawable.placeholder_image
            Picasso.get()
                .load(defaultImageUrl)
                .into(holder.imageThumbnail)
        }

        holder.itemView.setOnClickListener {
            // Panggil listener saat item diklik
            onItemClickListener?.invoke(article)
        }
    }


    override fun getItemCount(): Int {
        return articles.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
    }
}

