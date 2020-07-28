package com.example.noticeboard

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Post(
    val photo:Int,
    val PostID:Int,
    val Title:String,
    val Creator:String,
    val PostDate: String,
    val ViewCount:Int,
    val PostContents:String?
)

