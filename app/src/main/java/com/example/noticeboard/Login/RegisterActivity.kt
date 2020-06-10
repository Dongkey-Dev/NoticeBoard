package com.example.noticeboard.Login

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.noticeboard.R
import com.example.noticeboard.http.HttpTask
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@RegisterActivity
    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutConfirmPassword: TextInputLayout

    private lateinit var textInputEditTextName: TextInputEditText
    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText
    private lateinit var textInputEditTextConfirmPassword: TextInputEditText

    private lateinit var appCompatButtonRegister: AppCompatButton
    private lateinit var appCompatTextViewLoginLink: AppCompatTextView

    private lateinit var inputValidation: InputValidation

    operator fun JSONArray.iterator(): Iterator<JSONObject> =
        (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()

    }

    private fun initViews() {
        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView

        textInputLayoutName = findViewById<View>(R.id.textInputLayoutName) as TextInputLayout
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutConfirmPassword = findViewById<View>(R.id.textInputLayoutConfirmPassword) as TextInputLayout

        textInputEditTextName = findViewById<View>(R.id.textInputEditTextName) as TextInputEditText
        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText
        textInputEditTextConfirmPassword = findViewById<View>(R.id.textInputEditTextConfirmPassword) as TextInputEditText

        appCompatButtonRegister = findViewById<View>(R.id.appCompatButtonRegister) as AppCompatButton

        appCompatTextViewLoginLink = findViewById<View>(R.id.appCompatTextViewLoginLink) as AppCompatTextView

    }

    /**     * This method is to initialize listeners     */    private fun initListeners() {
        appCompatButtonRegister!!.setOnClickListener(this)
        appCompatTextViewLoginLink!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.appCompatButtonRegister -> postData()

            R.id.appCompatTextViewLoginLink -> finish()
        }
    }

    private fun postData() {
        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextName,
                textInputLayoutName,
                getString(R.string.error_message_name))) {
            return        } else if (!inputValidation!!.isInputEditTextEmail(
                textInputEditTextEmail,
                textInputLayoutEmail,
                getString(R.string.error_message_email))) {
            return        } else if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextPassword,
                textInputLayoutPassword,
                getString(R.string.error_message_password))) {
            return        } else if (!inputValidation!!.isInputEditTextMatches(
                textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return        } else{
            Snackbar.make(nestedScrollView!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()

            val json = JSONObject()
            json.put("email", textInputEditTextEmail.text.toString())
            json.put("username", textInputEditTextName.text.toString())
            json.put("password", textInputEditTextPassword.text.toString())

            progressBar.visibility = View.VISIBLE
            HttpTask({                progressBar.visibility = View.INVISIBLE
                if (it == null) {
                println("connection error")
                return@HttpTask                }
                println(it)
                val json_res = JSONObject(it)
                if (json_res.getString("status").equals("true")) {
                    Snackbar.make(nestedScrollView!!, json_res.getString("message"), Snackbar.LENGTH_LONG).show()
                    emptyInputEditText()
                    finish()
                } else {
                    Log.d("psot Data:::::::", json_res.getString("message"))
                    Snackbar.make(nestedScrollView!!, json_res.getString("message"), Snackbar.LENGTH_LONG).show()
                }
            }).execute("POST", "http://192.168.1.111/KotlinExample/LoginRegistration/register.php", json.toString())
        }

    }

    private fun emptyInputEditText() {
        textInputEditTextName!!.text = null
        textInputEditTextEmail!!.text = null
        textInputEditTextPassword!!.text = null
        textInputEditTextConfirmPassword!!.text = null    }

    companion object {
        val TAG = "RegisterActivity.."    }
}

//
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Build
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.android.volley.Response
//import com.example.noticeboard.Adapters.Adapter
//import com.example.noticeboard.Adapters.MainData
//import com.example.noticeboard.LoginActivity
//import com.example.noticeboard.R
//import kotlinx.android.synthetic.main.activity_main.*
//import org.json.JSONObject
//
//
//class RegisterActivity : AppCompatActivity() {
//    private var et_id: EditText? = null
//    private var et_pass: EditText? = null
//    private var et_name: EditText? = null
//    private var et_age: EditText? = null
//    private var btn_register: Button? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        //아이디값 찾아주기
//        et_id = findViewById(R.id.et_id)
//        et_pass = findViewById(R.id.et_pass)
//        et_name = findViewById(R.id.et_name)
//        et_age = findViewById(R.id.et_age)
//        //회원가입 버튼 클릭 시 수행
//        btn_register = findViewById(R.id.btn_register)
//        btn_register.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(view: View?) {
//                val userID = et_id.getText().toString()
//                val userPass = et_id.getText().toString()
//                val userName = et_name.getText().toString()
//                val userAge = et_age.getText().toString().toInt()
//                val responseListener: Response.Listener<String?> = object : Response.Listener<String?>() {
//                    fun onResponse(response: String?) {
//                        try {
//                            val jsonObject = JSONObject(response)
//                            val success = jsonObject.getBoolean("success")
//                            //회원가입 성공시
//                            if (success) {
//                                Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
//                                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
//                                startActivity(intent)
//                                //회원가입 실패시
//                            } else {
//                                Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
//                                return
//                            }
//                        } catch (e: JSONException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//                //서버로 Volley를 이용해서 요청
//                val registerRequest =
//                    RegisterRequest(userID, userPass, userName, userAge, responseListener)
//                val queue = Volley.newRequestQueue(this@RegisterActivity)
//                queue.add(registerRequest)
//            }
//        })
//    }
//}