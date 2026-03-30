package com.example.composetutorial.mainUI.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.model.OnboardingPage
import com.example.composetutorial.navigation.onboardingNav
import kotlinx.coroutines.delay

@Composable
fun OnBoardingSplash(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFFDBCBFF), Color(0xFFFFFFFF))))) {
        Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.title_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(200.dp).width(200.dp).clip(RoundedCornerShape(20.dp))
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = stringResource(R.string.splash_text), fontSize = 35.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(
                R.font.mplus_rounded1c_bold)))

            Spacer(Modifier.height(150.dp))

            Spacer(modifier = Modifier.height(100.dp))

            LinearProgressIndicator(
                modifier = Modifier
                    .width(300.dp)
                    .height(6.dp),
                color = Color(0xFF39BBF6),
            )
            LaunchedEffect(Unit) {
                delay(2000)
                navController.navigate(onboardingNav)
            }
        }
    }
}

@Preview
@Composable
fun PreviewOnboardingSplash() {
    //OnBoardingSplash()
}