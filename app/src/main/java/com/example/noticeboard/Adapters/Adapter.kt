package com.example.noticeboard.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noticeboard.R
import kotlinx.android.synthetic.main.post.view.*

class Adapter(private  val postlist: List<MainData>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    override fun getItemCount() = postlist.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.dongkey)
        val post_Title: TextView = itemView.postTitle
        val post_Creator: TextView = itemView.postCreator
        val post_ViewCount: TextView = itemView.viewCount
        val post_date : TextView = itemView.postDate
    }
}