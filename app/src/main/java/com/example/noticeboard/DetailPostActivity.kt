package com.example.noticeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_detail_post_noimg.*
import kotlinx.android.synthetic.main.activity_detail_post_noimg.article_body
import kotlinx.android.synthetic.main.activity_detail_post_noimg.article_title
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.lang.StringBuilder

class DetailPostActivity : AppCompatActivity() {

    private var Creator: TextView? = null
    private var Title: TextView? = null
    private var Content: TextView? = null
    private var PostDate: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val receivedINTENT : Intent = getIntent()
        if (receivedINTENT.getBooleanExtra("hasIMG",false)){
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_post)
        } else {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_detail_post_noimg)
        }

        val creator  = receivedINTENT.getStringExtra("Creator")
        val title  = receivedINTENT.getStringExtra("Title")
        val time_stamp  = receivedINTENT.getStringExtra("Date")

        Log.d("creator, title, time_stamp of received intent ", creator + title + time_stamp)

        getPostDetail(creator,title,time_stamp)
        article_creator.setText(creator)
        article_postdate.setText(time_stamp)
        article_title.setText(title)

    }

    private fun getPostDetail(C:String?, T:String?, TS:String?){

        article_title.setText(T)
        article_postdate.setText(TS)
        article_creator.setText(C)

        var que = Volley.newRequestQueue(this@DetailPostActivity)

        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_GET_POST_DETAIL_NO_IMG,
            Response.Listener<String>() { response ->
                try{
                    Log.d("print response", response)
                    val resJSON = JSONObject(response)
                    article_body.setText(resJSON.getString("postcontents"))
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }, object : Response.ErrorListener{
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    Log.d("fail", "onErrorResponse()")
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["title"] = T.toString()
                params["creator"] = C.toString()
                params["postdate"] = TS.toString()
                return params
            }
        }
        que!!.add(stringRequest!!)
    }
}
