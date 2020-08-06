package com.example.noticeboard.Dialog

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Window
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.noticeboard.MainActivity
import com.example.noticeboard.R
import com.example.noticeboard.Volley.EndPoints
import com.example.noticeboard.Volley.ExecuteVl

class MyDialog(context : Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : MyDialogOKClickedListener

    fun start(content : String,userid : String?, postTitle : String?, postDate : String? ) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.delete_dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        lblDesc = dlg.findViewById(R.id.content)
        lblDesc.text = content

        btnOK = dlg.findViewById(R.id.ok)
        btnOK.setOnClickListener {
                            Log.d("delete step 1, fun deletePost head", "access")
                            ExecuteVl.deletePost(dlg.context,
                                EndPoints.URL_DELETE_POST,userid.toString(),postTitle.toString(),postDate.toString()){
                                success->
                                if (success){
                                    Log.d("delete step 4, fun deletePost head", "access")
                                    Toast.makeText(dlg.context, "Post Deleted", Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(dlg.context, "Delete Fail", Toast.LENGTH_LONG).show()
                                }
                            }
            dlg.dismiss()
        }

        btnCancel = dlg.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }

        dlg.show()
    }
    fun setOnOKClickedListener(listener: (String) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(content: String) {
                listener(content)
            }
        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(content : String)
    }
}