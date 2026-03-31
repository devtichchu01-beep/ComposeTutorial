package com.example.composetutorial

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.navigation.AppNavigation
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testScanPDF()
        setContent {
            AppNavigation()
        }
    }
    fun testScanPDF() {

        Thread {

            val root = File("/storage/emulated/0")

            val pdfs = root.walkTopDown()
                .filter {
                    it.isFile &&
                            it.name.lowercase().endsWith(".pdf")
                }
                .toList()

            Log.e(
                "PDF_TEST",
                "PDF found = ${pdfs.size}"
            )

            pdfs.forEach {

                Log.e(
                    "PDF_TEST",
                    "PDF path = ${it.absolutePath}"
                )
            }

        }.start()
    }
}


