package com.example.noticeboard.Login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.MainData
import com.example.noticeboard.LoginActivity
import com.example.noticeboard.R
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject


class RegisterActivity :AppCompatActivity(){

    private var registerUserId: EditText? = null
    private var registerUserPwd: EditText? = null
    private var registerEmail: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //getting it from xml
        registerUserId = register_userId as EditText
        registerUserPwd = register_Password as EditText
        registerEmail = register_Email as EditText

        register_btn.setOnClickListener {
            registUser()
        }
    }

    private fun registUser(){
        Log.d("success" , "start registUser()")
        val id = registerUserId?.text.toString()
        val pswd = registerUserPwd?.text.toString()
        val email = registerEmail?.text.toString()

        val que = Volley.newRequestQueue(this@RegisterActivity)
        //creating volley string request
        Log.d("success" , "creating volley string request start")
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_REGIST_USER,
            Response.Listener<String>() { response ->
                try {
                    Log.d("success" , "start try")
                    Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                } catch (e : JSONException) {
                    Log.d("fail" , "registUser() fail")
                    e.printStackTrace()
                    Log.d("fail", e.printStackTrace().toString())
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
                params["id"] = id
                params["pswd"] = pswd
                params["email"] = email
                Log.d("success" , "start params")
                return params
            }
        }
        que!!.add(stringRequest!!)
    }
}