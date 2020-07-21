package com.example.noticeboard

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noticeboard.Adapters.Adapter
import com.example.noticeboard.Adapters.MainData
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        android.Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postlist = getPost()

        mRecyclerView.adapter = Adapter(postlist)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.setHasFixedSize(true)
        }

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
    private fun getPost():List<MainData>{

        val list = ArrayList<MainData>()

        return list
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


