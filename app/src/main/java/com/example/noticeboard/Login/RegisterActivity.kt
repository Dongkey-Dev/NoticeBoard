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
import com.android.volley.Response
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.MainData
import com.example.noticeboard.LoginActivity
import com.example.noticeboard.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private var et_name: EditText? = null
    private var et_age: EditText? = null
    private var btn_register: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //아이디값 찾아주기
        et_id = findViewById(R.id.et_id)
        et_pass = findViewById(R.id.et_pass)
        et_name = findViewById(R.id.et_name)
        et_age = findViewById(R.id.et_age)
        //회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.btn_register)
        btn_register.setOnClickListener(object : View.OnClickListener() {
            fun onClick(view: View?) {
                val userID = et_id.getText().toString()
                val userPass = et_id.getText().toString()
                val userName = et_name.getText().toString()
                val userAge = et_age.getText().toString().toInt()
                val responseListener: Response.Listener<String?> = object : Response.Listener<String?>() {
                    fun onResponse(response: String?) {
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getBoolean("success")
                            //회원가입 성공시
                            if (success) {
                                Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                startActivity(intent)
                                //회원가입 실패시
                            } else {
                                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                                return
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
                //서버로 Volley를 이용해서 요청
                val registerRequest =
                    RegisterRequest(userID, userPass, userName, userAge, responseListener)
                val queue = Volley.newRequestQueue(this@RegisterActivity)
                queue.add(registerRequest)
            }
        })
    }
}