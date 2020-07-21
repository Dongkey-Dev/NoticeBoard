package com.example.noticeboard

data class Post(
    val photo:String,
    val PostID:Int,
    val Title:String,
    val Creator:String,
    val PostDate:String,
    val ViewCount:Int,
    val PostContents:String?
)

