package com.example.noticeboard

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil.setContentView
import com.example.noticeboard.Adapters.User
import com.example.noticeboard.http.HttpTask
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var txt_userName: AppCompatTextView
    private lateinit var txt_email: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val id = intent.getStringExtra("id")

        initViews()
        progressBar.visibility = View.VISIBLE
        HttpTask({            progressBar.visibility = View.INVISIBLE
            if (it == null) {
            println("connection error")
            return@HttpTask            }
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
                Log.d("userdata Data:::::::", userdata.toString())
                txt_userName.setText(userdata.username)
                txt_email.setText(userdata.email)
            } else {
                Log.d("psot Data:::::::", json_res.getString("message"))
                Snackbar.make(nestedScrollView!!, json_res.getString("message"), Snackbar.LENGTH_LONG).show()
            }
        }).execute("GET", "http://192.168.1.111/KotlinExample/LoginRegistration/get_user_detail.php?id="+id)

    }

    private fun initViews() {

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        txt_userName = findViewById<View>(R.id.txt_userName) as AppCompatTextView
        txt_email = findViewById<View>(R.id.txt_email) as AppCompatTextView
    }
}
