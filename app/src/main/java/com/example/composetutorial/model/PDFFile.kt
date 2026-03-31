package com.example.composetutorial.model

data class PDFFile(
    val id : Long,
    val imgSource : Int,
    val fileType:Int,
    val text : String,
    val date: String,
    val fileSize: Long,
    val path: String,
    val isStarred : Boolean = false
)
