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
import com.example.noticeboard.Login.RegisterActivity
import com.example.noticeboard.Volley.EndPoints
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var UserId : EditText? = null
    private var UserPswd : EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        UserId = et_id as EditText
        UserPswd = et_pass as EditText

        val REGISTER_ACTIVITY_INTENT = Intent(this, RegisterActivity::class.java)
        btn_register.setOnClickListener { startActivity(REGISTER_ACTIVITY_INTENT) }

        btn_login.setOnClickListener { loginUser() }
    }

    private fun loginUser(){

        UserId = et_id as EditText
        UserPswd = et_pass as EditText

        val id = UserId?.text.toString()
        val pswd = UserPswd?.text.toString()

        var que = Volley.newRequestQueue(this@LoginActivity)

        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_LOGIN_USER,
            Response.Listener<String>() { response ->
                try {
                    val res = JSONObject(response)
                    val boolean_res : Boolean = response!=null&&res.getString("login_result")=="pass"
                    Toast.makeText(applicationContext, res.getString("login_result"), Toast.LENGTH_SHORT).show()
                    if (response!=null && res.getString("login_result") == "pass"){
                        val ENTER_SERVICE_INTENT = Intent(this, MainActivity::class.java)
                        ENTER_SERVICE_INTENT.putExtra("user_id",res.getString("user_id"))
                        try{
                            ENTER_SERVICE_INTENT.putExtra("profileImage", res.getInt("profileImage"))
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                        startActivity(ENTER_SERVICE_INTENT)
                    } else {
                        Toast.makeText(applicationContext, "wrong user", Toast.LENGTH_SHORT).show()
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
                params["id"] = id
                params["pswd"] = pswd
                Log.d("success" , "start params" + params)
                return params
            }
        }
        que!!.add(stringRequest!!)
    }
}
