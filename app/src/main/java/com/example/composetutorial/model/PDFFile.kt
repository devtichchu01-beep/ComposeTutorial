package com.example.composetutorial.model

data class PDFFile(
    val imgSource : Int,
    val isStarred: Boolean,
    val fileType:Int,
    val text : String,
    val date: String,
)
