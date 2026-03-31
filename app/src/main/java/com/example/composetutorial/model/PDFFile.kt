package com.example.composetutorial.model

data class PDFFile(
    val id : Int,
    val imgSource : Int,
    val fileType:Int,
    val text : String,
    val date: String,
    val isStarred : Boolean = false
)
