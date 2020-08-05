package com.example.noticeboard.Volley

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

object ExecuteVl{
    fun deletePost(context: Context,URL:String, Cre : String, Tit : String, Postdate : String, success: (Boolean) -> Unit) {

        val myJson = JSONObject()
        val requestBody = myJson.toString()
        /* myJson에 아무 데이터도 put 하지 않았기 때문에 requestBody는 "{}" 이다 */

        val Request = object : StringRequest(Method.POST, URL , Response.Listener { response ->
            Log.d("Server Response:",response)
            success(true)
        }, Response.ErrorListener { error ->
            Log.d("ERROR", "Fail Delete Post Request: $error")
            success(false)
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["title"] = Tit
                params["creator"] = Cre
                params["postdate"] = Postdate
                return params
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
            /* getBodyContextType에서는 요청에 포함할 데이터 형식을 지정한다.
             * getBody에서는 요청에 JSON이나 String이 아닌 ByteArray가 필요하므로, 타입을 변경한다. */
        }
        Volley.newRequestQueue(context).add(Request)
    }
}