package com.example.noticeboard

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.MainData
import com.example.noticeboard.Login.RegisterActivity
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post.*
import kotlinx.android.synthetic.main.post_list.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        android.Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receviedIntent : Intent = getIntent()
        main_id.setText(receviedIntent.getStringExtra("user_id"))
        val profileImg : Int = receviedIntent.getIntExtra("profileImage", 0)
        if (profileImg!=0){
            profileImage.setImageResource(profileImg)
        }else {
            profileImage.setImageDrawable(R.id.dongkey.toDrawable())
        }
        getPost() //Start get Post from php server

        val POST_DETAIL = Intent(this, DetailPostActivity::class.java)
//        postList.setOnClickListener { startActivity(POST_DETAIL) }

        val POSTING = Intent(this, PostingActivity::class.java)
        POSTING.putExtra("Creator", receviedIntent.getStringExtra("user_id"))
        newPosting.setOnClickListener{ startActivity(POSTING)}
    }
    public fun setDetailPost(c:String,t:String,d:String){

    }

    private fun connectPost(j : String):List<Post>{
        val sizejson = JSONArray(j)
        val list = ArrayList<Post>()

        for (i in 0 until sizejson.length()){
            val jObject = sizejson.getJSONObject(i)
            val postid = jObject.getInt("postid")
            Log.d("postid",postid.toString())
            val title = jObject.getString("title")
            Log.d("title",title.toString())
            val creator = jObject.getString("creator")
            Log.d("creator",creator.toString())
            var post_date = jObject.getString("postdate")
            Log.d("post_date",post_date.toString())
//            val postday : CharSequence = post_date
            val postdate = jObject.getString("postdate").toString()
            Log.d("postdate",postdate.toString())
            val viewcount = jObject.getInt("viewcount")
            Log.d("viewcount",viewcount.toString())
            val postcontent = jObject.getString("postcontents")
            Log.d("postcontent",postcontent.toString())
            val photo = jObject.get("photo")
            val drawable = R.drawable.dongkey

            val item = Post(drawable,postid, title,creator, postdate, viewcount, postcontent)
            list += item
        }
        return list
    }
    private fun commandSet(input:String){

        Log.d("step1", input)
        val postlist = connectPost(input)
        Log.d("step2", "postlist")
        mRecyclerView.adapter = Adapter(postlist)
        Log.d("step3", "mRecyclerView 1")
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        Log.d("step4", "mRecyclerView 2")
        mRecyclerView.setHasFixedSize(true)
    }
    private fun getPost(){

        val que = Volley.newRequestQueue(this@MainActivity)
        val stringRequest = object : StringRequest(
            Request.Method.GET, EndPoints.URL_GET_POST, Response.Listener<String>() { response ->
                try {
                    commandSet(response)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    Log.d("fail", "onErrorResponse()")
                }
            })
        {}
        que!!.add(stringRequest!!)
    }

    private fun generateDummyList(size : Int) : List<MainData> {

        val list = ArrayList<MainData>()

        for (i in 0 until size) {
            val drawable = R.drawable.dongkey

            val item = MainData(drawable, "리사이클러뷰 테스트 ${i}","동키", "20200526", i, "ㅁㅁㄴㅇ")
            list += item
            }
        return list
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0

        for (idx in grantResults.indices){
            if (grantResults[idx]== PackageManager.PERMISSION_GRANTED){
                Log.d("testGrantResult","${idx} is Granted")
            } else {
                Log.d("testGrantResult","${idx} is not allowed")
            }
        }
    }
    fun checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        for (permission : String in permission_list){

            var chk = checkCallingOrSelfPermission(permission)

            if(chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0)
                break
            }
        }
    }
}


