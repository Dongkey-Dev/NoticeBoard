package com.example.noticeboard.Volley

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.MainActivity
import org.json.JSONObject
import java.util.HashMap

class ExecuteVolley(method : Int,context : Context){

    val stringRequest = object : StringRequest(
        method, EndPoints.URL_LOGIN_USER,
        Response.Listener<String>() { response ->
            try {
                Log.d("response : ", response)
                } catch (e : Exception) {
                    e.printStackTrace()
                }
        },
        object : Response.ErrorListener{
            override fun onErrorResponse(volleyError: VolleyError) {
                Log.d("fail" , "onErrorResponse()")
            }
        })
    {
        @Throws(AuthFailureError::class)
        override fun getParams(): Map<String, String> {
            val params = HashMap<String, String>()

            Log.d("success" , "start params" + params)
            return params
        }
    }
    que.add(stringRequest)
}