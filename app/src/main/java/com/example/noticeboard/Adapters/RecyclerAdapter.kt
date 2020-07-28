//package com.example.noticeboard.Adapters
//
//import android.content.Context
//import android.media.Image
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.databinding.adapters.ImageViewBindingAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.noticeboard.Post
//import com.example.noticeboard.R
//import kotlinx.android.synthetic.main.post.view.*
//import org.w3c.dom.Text
//
//class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MainViewHolder>() {
//
//    var items: MutableList<MainData> =
//        mutableListOf(
////            MainData("dongkey", "test1", "동호", "20200520", 10, "test my PostContents"),
////            MainData("dongkey", "test2", "동호", "20200520", 10, "test my PostContents"),
////            MainData("dongkey", "test3", "동호", "20200520", 10, "test my PostContents")
//        )
//
//    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainViewHolder(parent)
//
//
//    override fun getItemCount(): Int = items.size
//
//
//    override fun onBindViewHolder(holer: MainViewHolder, position: Int) {
//        items[position].let { item ->
//            with(holer) {
//                title.text = item.Title
//                creator.text = item.Creator
//                postdate.text = item.PostDate
//                viewcount.text = item.ViewCount.toString()
//            }
//        }
//    }
//
//    inner class MainViewHolder(parent: ViewGroup) :
//        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post, parent, false)) {
//        val photo = itemView?.findViewById<ImageView>(R.id.dongkey)
//        val title = itemView.postTitle
//        val creator = itemView.postCreator
//        val postdate = itemView.postDate
//        val viewcount = itemView.viewCount
//
//        fun bind(post: Post, context: Context) {
//            /* dogPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
//            이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
//            if (post.photo != "") {
//                val resourceId = context.resources.getIdentifier(post.photo, "drawable", context.packageName)
//                photo?.setImageResource(resourceId)
//            } else {
//                photo?.setImageResource(R.mipmap.ic_launcher)
//            }
//        }
//    }
//}