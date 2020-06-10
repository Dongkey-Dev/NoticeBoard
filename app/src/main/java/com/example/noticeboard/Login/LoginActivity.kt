package com.example.noticeboard.Login

import android.content.Intent
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.NestedScrollView
import com.example.noticeboard.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private val activity = this@LoginActivity
    private lateinit var nestedScrollView: NestedScrollView

    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    private lateinit var textInputEditTextEmail: TextInputEditText
    private lateinit var textInputEditTextPassword: TextInputEditText

    private lateinit var appCompatButtonLogin: AppCompatButton

    private lateinit var textViewLinkRegister: AppCompatTextView

    private lateinit var inputValidation: InputValidation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar!!.hide()
        initViews()
        initListeners()
        initObjects()

    }

    private fun initViews() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        nestedScrollView = findViewById<View>(R.id.nestedScrollView) as NestedScrollView

        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword = findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout

        textInputEditTextEmail = findViewById<View>(R.id.textInputEditTextEmail) as TextInputEditText
        textInputEditTextPassword = findViewById<View>(R.id.textInputEditTextPassword) as TextInputEditText

        appCompatButtonLogin = findViewById<View>(R.id.appCompatButtonLogin) as AppCompatButton

        textViewLinkRegister = findViewById<View>(R.id.textViewLinkRegister) as AppCompatTextView

    }

    private fun initListeners() {

        appCompatButtonLogin!!.setOnClickListener(this)
        textViewLinkRegister!!.setOnClickListener(this)
    }

    private fun initObjects() {

        inputValidation = InputValidation(activity)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonLogin -> verifyFromSQLite()
            R.id.textViewLinkRegister -> {
                val intentRegister = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intentRegister)
            }
        }
    }

    private fun verifyFromSQLite() {

        if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextEmail!!,
                textInputLayoutEmail!!,
                getString(R.string.error_message_email))) {
            return        } else if (!inputValidation!!.isInputEditTextFilled(
                textInputEditTextPassword!!,
                textInputLayoutPassword!!,
                getString(R.string.error_message_password))) {
            return        } else {
            val json = JSONObject()
            json.put("email", textInputEditTextEmail.text.toString())
            json.put("password", textInputEditTextPassword.text.toString())

            progressBar.visibility = View.VISIBLE            HttpTask({                progressBar.visibility = View.INVISIBLE                if (it == null) {
                println("connection error")
                return@HttpTask                }
                println(it)
                val json_res = JSONObject(it)
                if (json_res.getString("status").equals("true")) {
                    var userdata = User()
                    var jsonArray = JSONArray(json_res.getString("data"))
                    for (i in 0..(jsonArray.length() - 1)) {
                        val item = jsonArray.getJSONObject(i)
                        userdata.id = item.getString("id")
                        userdata.username = item.getString("username")
                        userdata.email = item.getString("email")
                    }
                    emptyInputEditText()
                    val intent = Intent(activity, HomeActivity::class.java)
                    intent.putExtra("id", userdata.id)
                    startActivity(intent)
                    Log.d("userdata Data:::::::", userdata.toString())
                } else {
                    Log.d("psot Data:::::::", json_res.getString("message"))
                    Snackbar.make(nestedScrollView!!, json_res.getString("message"), Snackbar.LENGTH_LONG).show()
                }

            }).execute("POST", "http://192.168.1.111/KotlinExample/LoginRegistration/login.php", json.toString())
        }
    }

    private fun emptyInputEditText() {
        textInputEditTextEmail!!.text = null        textInputEditTextPassword!!.text = null    }

}