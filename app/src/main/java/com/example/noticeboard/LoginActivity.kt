package com.example.noticeboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val btn_post = findViewById<Button>(R.id.posting)
//        btn_post.setOnClickListener{
//            val nextIntent = Intent(this, PostingActivity::class.java)
//            startActivity(nextIntent)
//        }

        login.setOnClickListener {
            val nextIntent = Intent(this,MainActivity::class.java )
            startActivity(nextIntent)
        }

    }
}
