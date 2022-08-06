package com.example.news

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class NewsAdapter(val context: Context, val articlesList: List<Article>):
    RecyclerView.Adapter<NewsAdapter.ViewHolder>()
{

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var image: ImageView
        var publishedAt: TextView
        var source: TextView
        var title: TextView
        var desc: TextView

        init {
            image = itemView.findViewById<ImageView>(R.id.imageView)
            publishedAt = itemView.findViewById<TextView>(R.id.published_at)
            source = itemView.findViewById<TextView>(R.id.source)
            title = itemView.findViewById<TextView>(R.id.news_title)
            desc = itemView.findViewById<TextView>(R.id.news_desc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.publishedAt.text = articlesList[position].publishedAt
        holder.source.text      = articlesList[position].source.name
        holder.title.text       = articlesList[position].title
        holder.desc.text        = Html.fromHtml(articlesList[position].description)

        try{
            Picasso.get()
                .load(articlesList[position].urlToImage)
                .into(holder.image)
        }catch(e: Exception){

        }
    }

    override fun getItemCount(): Int
    {
        return articlesList.size
    }
}