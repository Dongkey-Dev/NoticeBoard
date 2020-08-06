package com.example.noticeboard

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.Adapter.ViewHolder
import com.example.noticeboard.Adapters.MainData
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.lang.Exception
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        android.Manifest.permission.INTERNET
    )
    public var USER_ID : String? = null

    fun refresh(){
        getPost()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val receviedIntent : Intent = getIntent()
        USER_ID = receviedIntent.getStringExtra("user_id")
        main_id.setText(receviedIntent.getStringExtra("user_id"))
        var option = receviedIntent.getStringExtra("option")

        val profileImg : Int = receviedIntent.getIntExtra("profileImage", 0)
        if (profileImg!=0){
            profileImage.setImageResource(profileImg)
        }else {
            profileImage.setImageDrawable(R.id.dongkey.toDrawable())
        }
        Thread.sleep(10)
        getPost() //Start get Post from php server

        val POSTING = Intent(this, PostingActivity::class.java)
        POSTING.putExtra("Creator", receviedIntent.getStringExtra("user_id"))
        newPosting.setOnClickListener{ startActivity(POSTING)}

        checkbox_viewMyPost.setOnClickListener{

            if (checkbox_viewMyPost.isChecked()){
                getPost(true)
            } else{
                getPost()
            }
        }
    }

    private fun connectPost(j : String):List<Post>{
        val sizejson = JSONArray(j)
        val list = ArrayList<Post>()

        for (i in 0 until sizejson.length()){
            val jObject = sizejson.getJSONObject(i)
            val postid = jObject.getInt("postid")
            val title = jObject.getString("title")
            val creator = jObject.getString("creator")
            var post_date = jObject.getString("postdate")
            val postdate = jObject.getString("postdate").toString()
            val viewcount = jObject.getInt("viewcount")
            val postcontent = jObject.getString("postcontents")
            val photo = jObject.get("photo")
            val drawable = R.drawable.dongkey

            val item = Post(drawable,postid, title,creator, postdate, viewcount, postcontent)
            list += item
        }
        return list
    }
    private fun commandSet(input:String){
        val postlist = connectPost(input)
        mRecyclerView.adapter = Adapter(postlist, USER_ID)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
    }
    private fun getPost(ViewOnlyMyPost:Boolean = false){

        val que = Volley.newRequestQueue(this@MainActivity)
        val stringRequest = object : StringRequest(
            Request.Method.POST,EndPoints.URL_GET_POST, Response.Listener<String>() { response ->
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
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["creator"] = USER_ID.toString()
                params["viewonlymypost"] =  ViewOnlyMyPost.toString()
                return params
            }
        }
        que!!.add(stringRequest!!)
    }

    fun deletePost(creator : String?, title : String?, postdate : String?){

        val que = Volley.newRequestQueue(this@MainActivity)
        val stringRequest = object : StringRequest(
            Request.Method.POST,EndPoints.URL_DELETE_POST, Response.Listener<String>() { response ->
                try {
                    Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
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
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["creator"] = creator.toString()
                params["title"] =  title.toString()
                params["postdate"] =  postdate.toString()
                return params
            }
        }
        que!!.add(stringRequest!!)
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


