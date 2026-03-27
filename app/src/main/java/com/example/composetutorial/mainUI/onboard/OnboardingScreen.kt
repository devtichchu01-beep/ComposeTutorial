package com.example.composetutorial.mainUI.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.delay

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, navController: NavController) {
    val state = viewModel.state
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val pages = listOf(
//        OnboardingPage(
//            image = R.drawable.title_bg,
//            isIntro = true
//        ),
        OnboardingPage(R.drawable.splash1_bg, "Read all documents", "Read documents in various formats"),
        OnboardingPage(R.drawable.splash2, "Scan to PDF", "Easily scan document to PDF"),
        OnboardingPage(R.drawable.splash3_bg, "Highlight & note", "Take notes with underlining, drawing.."),
        OnboardingPage(R.drawable.splash4_bg, "Add signature & stickers", "Add your own signatures and stickers"),
    )

    val pagerState = rememberPagerState(pageCount = {pages.size})

    LaunchedEffect(state.currentPage) {
        if(state.currentPage == 0) {
            delay(2000)
            viewModel.send(OnboardingIntent.NextPage)
        }
        pagerState.animateScrollToPage(state.currentPage)
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            viewModel.send(OnboardingIntent.SetPage(pagerState.currentPage))
        }
    }
    Box() {
        Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFFDBCBFF), Color(0xFFFFFFFF))))) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),

            ) { page ->
                OnboardingItem(pages[page])
            }

//            if (state.currentPage != 0) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(end = 20.dp, bottom = 5.dp)
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = state.currentPage == index
                        Box(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 20.dp).clip(CircleShape).size(8.dp)
                                .background(
                                    if (isSelected) {
                                        Color(0xFFFFC107)
                                    } else {
                                        Color(0xE8F5EC96)
                                    }
                                )
                        )
                    }
                }

                Button(
                    onClick = {
                        if (state.currentPage < pages.lastIndex) {
                            viewModel.send(OnboardingIntent.NextPage)
                        } else {
//                            context.startActivity(Intent(context, LanguageActivity::class.java))
                            navController.navigate("bottom")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp, bottom = 40.dp).height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(
                            0xFF0485F8
                        )
                    )
                ) {
                    Text(
                        when (state.currentPage) {
                            pages.lastIndex -> "Complete"
                            else -> "Next"
                        },
                        fontSize = 18.sp
                    )
                }
            }
//        }
    }
}


@Composable
fun OnboardingItem(page : OnboardingPage) {
    if(page.isIntro) {
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFFDBCBFF), Color(0xFFFFFFFF))))) {
            Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Image(
                    painter = painterResource(id = page.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp).width(200.dp).clip(RoundedCornerShape(20.dp))
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "PDF Manager\nReader, Scanner", fontSize = 35.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(
                    R.font.mplus_rounded1c_bold)))

                Spacer(Modifier.height(150.dp))

                Spacer(modifier = Modifier.height(100.dp))

                LinearProgressIndicator(
                    modifier = Modifier
                        .width(300.dp)
                        .height(6.dp),
//                        .drawWithContent {
//                            drawContent()
//                            val progressWidth = size.width * progress
//
//                            drawRect(
//                                brush = Brush.horizontalGradient(
//                                    listOf(
//                                        Color(0xFF39BBF6),
//                                        Color(0xFF7335F1)
//                                    )
//                                ),
//                                size = Size(progressWidth, size.height)
//                            )
//                        },
                    color = Color(0xFF39BBF6),
//                    trackColor = Color.Transparent
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Image(
                    painter = painterResource(id = page.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(500.dp).width(400.dp).clip(RoundedCornerShape(50.dp)).padding(horizontal = 20.dp).padding(start = 10.dp, end = 10.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
                Text(text = page.title, fontSize = 30.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                    Font(R.font.inter_28pt_regular)), fontWeight = FontWeight.Bold)
                Text(text = page.desc, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 20.dp), fontFamily = FontFamily(Font(
                    R.font.inter_28pt_regular)))
            }
        }
    }
}
@Preview
@Composable
fun PreviewOnboardingScreenMVI() {
    //OnboardingScreenMVI()
}