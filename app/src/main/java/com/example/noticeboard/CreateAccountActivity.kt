package com.example.noticeboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil.findBinding
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.noticeboard.Adapters.Adapter
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_main.*

class CreateAccountActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        register_btn.setOnClickListener{
            VolleyService.testVolley(this) {testSuccess ->
                if (testSuccess) {
                    Toast.makeText(this, "connect success", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "conenct fail", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}