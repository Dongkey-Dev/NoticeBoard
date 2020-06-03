package com.example.noticeboard

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.MainData
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.net.URL
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        android.Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postlist = generateDummyList(500)

        mRecyclerView.adapter = Adapter(postlist)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        }
//    fun fetchJson(){  // connect to webserver
//        val url = URL("http://dongkeydev.dothome.co.kr/join.php")
//        val request = Request.Builder().url(url).build()
//        val client = OkHttpClient()
//        client.newCall(request).enqueue(object : Callback{
//            override fun onResponse(call: Call?, response: Response?){
//                val body = response?.body()?.string()
//                println("Success to execute request! : $body")
//
//                //parsing to Gson
//
//                val gson = GsonBuilder().create()
//                val list = gson.fromJson(body, post::class.java)
//            }
//        })
//    }

    fun checkPermission(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return;
        }
        for (permission : String in permission_list){

            var chk = checkCallingOrSelfPermission(permission)

            if(chk == PackageManager.PERMISSION_DENIED){
                requestPermissions(permission_list, 0)
                break
            }
        }
    }

    private fun generateDummyList(size : Int) : List<MainData> {

        val list = ArrayList<MainData>()

        for (i in 0 until size) {
            val drawable = R.drawable.dongkey

            val item = MainData(drawable, "리사이클러뷰 테스트 ${i}","동키", "20200526", i, "ㅁㅁㄴㅇ")
            list += item
            }
        return list
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var idx = 0

        for (idx in grantResults.indices){
            if (grantResults[idx]== PackageManager.PERMISSION_GRANTED){
                Log.d("testGrantResult","${idx} is Granted")
            } else {
                Log.d("testGrantResult","${idx} is not allowed")
            }
        }
    }
}


