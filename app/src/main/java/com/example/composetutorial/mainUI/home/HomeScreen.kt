package com.example.composetutorial.mainUI.home

import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetutorial.R
import com.example.composetutorial.mainUI.bottom.BottomScreen
import com.example.composetutorial.model.BottomItem
@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize().background(brush = Brush.horizontalGradient(colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))))) {
        Text(text = "PDF Manager", fontSize = 30.sp, modifier = Modifier.align(alignment = Alignment.TopStart).padding(start = 20.dp, top = 50.dp), color = Color.White)
        Image(
            painter = painterResource(R.drawable.ic_diamond),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 50.dp, end = 20.dp).height(30.dp).width(30.dp).align(alignment = Alignment.TopEnd)
        )
        Spacer(modifier = Modifier.height(50.dp))

        var text by remember{ mutableStateOf("") }

        TextField(
            value = text,
            onValueChange = {text = it},
            label = {Text("Search document..")},
            modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp).clip(RoundedCornerShape(30.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)).background(color = Color.White).height(530.dp).align(alignment = Alignment.BottomCenter)
        ) {

        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}