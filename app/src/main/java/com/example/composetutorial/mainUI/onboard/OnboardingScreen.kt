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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
import com.example.composetutorial.navigation.bottomNav

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, navController: NavController) {
    val state = viewModel.state

    val pages = listOf(
        OnboardingPage(R.drawable.splash1_bg, stringResource(R.string.splash_title1), stringResource(R.string.splash_desc1)),
        OnboardingPage(R.drawable.splash2, stringResource(R.string.splash_title2), stringResource(R.string.splash_desc2)),
        OnboardingPage(R.drawable.splash3_bg, stringResource(R.string.splash_title3), stringResource(R.string.splash_desc3)),
        OnboardingPage(R.drawable.splash4_bg, stringResource(R.string.splash_title4), stringResource(R.string.splash_desc4)),
    )

    val pagerState = rememberPagerState(pageCount = {pages.size})

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            viewModel.send(OnboardingIntent.SetPage(pagerState.currentPage))
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(colorResource(R.color.splash_trans1), colorResource(R.color.splash_trans2))))) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),

            ) { page ->
                OnboardingItem(pages[page])
            }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(end = 20.dp, bottom = 5.dp, top = 10.dp)
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = state.currentPage == index
                        Box(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 20.dp).clip(CircleShape).size(8.dp)
                                .background(
                                    if (isSelected) {
                                        colorResource(R.color.select_dot)
                                    } else {
                                        colorResource(R.color.unselected_dot)
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
                            navController.navigate(bottomNav)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
                        .padding(bottom = 40.dp).height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.cyan)
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
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(colorResource(R.color.splash_trans1), colorResource(R.color.splash_trans2))))) {
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

                Text(text = stringResource(R.string.splash_text),fontSize = 35.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(
                    R.font.mplus_rounded1c_bold)))

                Spacer(Modifier.height(150.dp))

                Spacer(modifier = Modifier.height(100.dp))

                LinearProgressIndicator(
                    modifier = Modifier
                        .width(300.dp)
                        .height(6.dp),
                    color = Color(0xFF39BBF6),
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
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(50.dp)).padding(horizontal = 20.dp).padding(start = 10.dp, end = 10.dp)
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