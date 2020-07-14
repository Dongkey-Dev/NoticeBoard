package com.example.noticeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Login.RegisterActivity
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var UserId : EditText? = null
    private var UserPswd : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerIntent = Intent(this, RegisterActivity::class.java)
        btn_register.setOnClickListener { startActivity(registerIntent) }

        btn_login.setOnClickListener { loginUser() }
    }

    private fun loginUser(){
        UserId = et_id as EditText
        UserPswd = et_pass as EditText

        var que = Volley.newRequestQueue(this@LoginActivity)

        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_LOGIN_USER,
            Response.Listener<String>() { response ->
                try {
                    Log.d("success" , "start try")
                    Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
                    val ENTER_SERVICE_INTENT = Intent(this, MainActivity::class.java)
                    startActivity(ENTER_SERVICE_INTENT)
                } catch (e : Exception) {
                    Log.d("fail" , "registUser() fail")
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
                params["id"] = UserId.toString()
                params["pswd"] = UserPswd.toString()
                Log.d("success" , "start params")
                return params
            }
        }
        que!!.add(stringRequest!!)
    }
}
