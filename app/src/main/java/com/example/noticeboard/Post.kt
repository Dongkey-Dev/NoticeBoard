package com.example.noticeboard

data class Post(
    val photo: String,
    val Title:String,
    val Creator:String,
    val PostDate:String,
    val ViewCount:Int,
    val PostContents:String?
) {
}
