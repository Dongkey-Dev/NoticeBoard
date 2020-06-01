package com.example.noticeboard

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.ResponseCache

object VolleyService {

    val url : String = "http://dongkeydev.dothome.co.kr/Register.php";

    fun testVolley(context: Context, success: (Boolean) -> Unit){

        val myJson = JSONObject()
        val requestBody = myJson.toString()

        val testRequest = object : StringRequest(Method.GET, url, Response.Listener { response -> println("서버 GET : $response")
        success(true)
        },Response.ErrorListener { error ->
            Log.d("Error", "서버 GET 실패 : $error")
            success(false)
        }) {
            override fun getBodyContentType(): String {
                return "application/jsonl charset=utf-8"
            }

            override fun getBody(): ByteArray {
                return requestBody.toByteArray()
            }
            /*  getBodyContextType : 요청에 포함할 테이터 형식 지정
            *  getBody : 타입이 ByteArray이므로 타입변경 */
        }

        Volley.newRequestQueue(context).add(testRequest)
    }

}