package com.example.noticeboard.Adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.noticeboard.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post.view.*

class Adapter(private  val postlist: List<Post>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

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
        holder.post_date.text = currentPost.PostDate.toString()

        val context=holder.itemView.context

        val DETAIL_POST = Intent(context, DetailPostActivity::class.java)
        DETAIL_POST.putExtra("Creator", holder.post_Creator.text.toString())
        DETAIL_POST.putExtra("Title", holder.post_Title.text.toString())
        DETAIL_POST.putExtra("Date", holder.post_date.text.toString())

        //default post img type
        DETAIL_POST.putExtra("hasIMG", false)

        holder.itemView.setOnClickListener{
            Log.d("SSS","${position} selected")
            Log.d("sss", holder.post_Title.text.toString() + "" + holder.post_date)
            context.startActivity(DETAIL_POST)
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