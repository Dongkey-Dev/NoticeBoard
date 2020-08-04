//package com.example.noticeboard.Volley
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.widget.Toast
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.VolleyError
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.example.noticeboard.MainActivity
//
//class ExecuteVolley : {
//
//    private fun ExecuteVolley(url : String ,  input : String , c : Context) : Any{
//
//        val context : Context = c
//
//        val que = Volley.newRequestQueue(context)
//        var stringRequest = object : StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String>() { response ->
//                try {
//                        return response
//                    } else {
//                        Toast.makeText(applicationContext, "wrong user", Toast.LENGTH_SHORT).show()
//                    }
//                } catch (e : Exception) {
//                    e.printStackTrace()
//                }
//            },
//            object : Response.ErrorListener{
//                override fun onErrorResponse(volleyError: VolleyError) {
//                    Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show()
//                    Log.d("fail" , "onErrorResponse()")
//                }
//            })
//        return 0
//    }
//    private  fun ExecuteVolley(getMethod : String  ) : Any{
//
//        return 0
//    }
//}