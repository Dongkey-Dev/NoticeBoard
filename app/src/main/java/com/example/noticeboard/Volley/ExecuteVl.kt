package com.example.noticeboard.Volley

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import android.widget.Adapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.*
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.MainActivity
import org.json.JSONObject
import java.lang.Exception

object ExecuteVl{

    fun deletePost(context: Context,URL:String, Cre : String, Tit : String, Postdate : String, success: (Boolean) -> Unit) {
        var que = Volley.newRequestQueue(context)

        Log.d("delete step 2, fun deletePost head", "access")
        Log.d("ExecuteVI input", URL +" "+Cre +" "+ Tit +" "+ Postdate)

        val Request = object : StringRequest(Method.POST, URL , Response.Listener { response ->
            try{
                Log.d("delete step 3, fun deletePost head", response)
                success(true)
            } catch (e:Exception){
                e.printStackTrace()
            }
        },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                    success(false)
                }
        })
        {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["title"] = Tit
                params["creator"] = Cre
                params["postdate"] = Postdate
                Log.d("success" , "start params" + params)
                return params
            }
            /* getBodyContextType에서는 요청에 포함할 데이터 형식을 지정한다.
             * getBody에서는 요청에 JSON이나 String이 아닌 ByteArray가 필요하므로, 타입을 변경한다. */
        }
        que!!.add(Request!!)
    }
}