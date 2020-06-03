package com.example.noticeboard.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noticeboard.R
import com.bumptech.glide.*
import com.bumptech.glide.request.RequestOptions
import com.example.noticeboard.Post
import kotlinx.android.synthetic.main.post.view.*

class Adapter(private  val postlist: JsonObj) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindItems

        val currentPost = postlist[position]

        holder.imageView.setImageResource(currentPost.photo)
        holder.post_Title.text = currentPost.Title
        holder.post_Creator.text = currentPost.Creator
        holder.post_ViewCount.text = currentPost.ViewCount.toString()
        holder.post_date.text = currentPost.PostDate

        if (position == 0) {
            holder.post_Title.setBackgroundColor(Color.YELLOW)
        }
    }

    override fun getItemCount(): Int{
        return postlist.result.count()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.dongkey)
        val post_Title: TextView = itemView.postTitle
        val post_Creator: TextView = itemView.postCreator
        val post_ViewCount: TextView = itemView.viewCount
        val post_date : TextView = itemView.postDate

        fun bindItems(data : Post){
            Glide.with(itemView.context).load(data.photo).apply(RequestOptions().override(600,600))
                .apply(RequestOptions.centerCropTransform()).into(itemView.dongkey)
            itemView.postTitle.text = data.Title
            itemView.postCreator.text = data.Creator
            itemView.postDate.text = data.PostDate
            itemView.viewCount.text = data.ViewCount.toString()
        }
    }
}