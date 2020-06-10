package com.example.noticeboard.Adapters

class User {
    var id:String? = null
    var username:String? = null
    var email:String? = null

    override fun toString(): String{
        return "User(id=$id, username=$username, email=$email)"
    }
}