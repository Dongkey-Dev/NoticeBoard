package com.example.noticeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_posting.*
import org.json.JSONObject
import java.util.HashMap

class PostingActivity : AppCompatActivity() {

    private var PostingTitle : EditText? = null
    private var PostingContents : EditText? = null
    private var PostingImgSrc : EditText?= null
    private var PostCreator : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posting)
        val receviedIntent : Intent = getIntent()

        PostingTitle = postingTitle as EditText
        PostingContents = postingContents as EditText
        PostCreator = receviedIntent.getStringExtra("Creator")

        btnUpdatePosting.setOnClickListener{update_posting(PostingTitle,PostCreator, PostingContents, PostingImgSrc)}
    }

    fun update_posting( title : EditText?, creator:String?, contents : EditText?, imgsrc : EditText?){
        val Title = title?.text.toString()
        val Contents = contents?.text.toString()
        val Creator= creator.toString()

        var que= Volley.newRequestQueue(this@PostingActivity)

        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_POSTING,
            Response.Listener<String>() { response ->
                try {
                    Log.d("response test", response)
                    val res = JSONObject(response)
                    Toast.makeText(applicationContext, res.getString("posting_result"), Toast.LENGTH_SHORT).show()
                    if (response!=null && res.getString("posting_result") == "success"){
                        val ENTER_SERVICE_INTENT = Intent(this, MainActivity::class.java)
                        ENTER_SERVICE_INTENT.putExtra("user_id", creator)
                        try{
                            startActivity( ENTER_SERVICE_INTENT )
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Posting Failed", Toast.LENGTH_SHORT).show()
                    }
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            },
            object : Response.ErrorListener{
                override fun onErrorResponse(volleyError: VolleyError) {
                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
                    Log.d("fail" , "onErrorResponse()")
                }
            })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["title"] = Title
                params["creator"] = Creator
                params["postcontents"] = Contents
                Log.d("success" , "start params" + params)
                return params
            }
        }
        que!!.add(stringRequest!!)
    }
}
