package com.example.noticeboard.Adapters

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.noticeboard.*
import com.example.noticeboard.Dialog.MyDialog
import com.example.noticeboard.Volley.EndPoints
import com.example.noticeboard.Volley.ExecuteVl
import kotlinx.android.synthetic.main.post.view.postCreator
import kotlinx.android.synthetic.main.post.view.postDate
import kotlinx.android.synthetic.main.post.view.postTitle
import kotlinx.android.synthetic.main.post.view.viewCount
import kotlinx.android.synthetic.main.post_list.view.*
import java.lang.reflect.Executable

class Adapter(private  val postlist: List<Post>, private  val userid : String?) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    private var prePostion : Int = -1;

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
            context.startActivity(DETAIL_POST)
        }

        holder.itemView.setOnLongClickListener{
            Log.d("check user same", holder.itemView.postCreator.text.toString() + " " + userid)
            if (holder.itemView.postCreator.text.toString() == userid ){
                val popup = PopupMenu(holder.itemView.context, it)
                popup.inflate(R.menu.menu_main)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.modify -> {
                            DETAIL_POST.putExtra("option", "modify")
                            DETAIL_POST.putExtra("userid", userid)
                            Log.d("step 1 userid",userid)
                            context.startActivity(DETAIL_POST)
                        }
                        R.id.delete -> {
                            val dlg = MyDialog(context)
                            dlg.setOnOKClickedListener{ content ->
                            }
                            dlg.start("Are you sure delete post?",userid,holder.itemView.postTitle.text.toString(), holder.itemView.postDate.text.toString())
                        }
                    }
                    true
                }
                popup.show()
            }
            return@setOnLongClickListener true
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