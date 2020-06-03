package com.example.noticeboard.Login

import com.android.volley.AuthFailureError

import com.android.volley.Request.Method.POST
import com.android.volley.Response

import com.android.volley.toolbox.StringRequest


class RegisterRequest(
    UserId: String,
    UserPassword: String,
    Email: String,
    PID: Int,
    listener: Response.Listener<String?>?
) :
    StringRequest(POST, URL, listener, null) {
    private val map: MutableMap<String, String>
    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }

    companion object {
        //서버 URL 설정(php 파일 연동)
        private const val URL = "http://dongkeydev.dothome.co.kr/Register.php"
    }

    //private Map<String, String>parameters;
    init {
        map = HashMap()
        map["userId"] = UserId
        map["UserPassword"] = UserPassword
        map["Email"] = Email
        map["PID"] = PID.toString() + ""
    }
}