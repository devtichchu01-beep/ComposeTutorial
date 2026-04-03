package com.example.composetutorial.mainUI.onboard

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.ui.text.TextStyle
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
        OnboardingPage(R.drawable.splash_2x, stringResource(R.string.splash_title1), stringResource(R.string.splash_desc1)),
        OnboardingPage(R.drawable.splash1_2x, stringResource(R.string.splash_title2), stringResource(R.string.splash_desc2)),
        OnboardingPage(R.drawable.splash2_2x, stringResource(R.string.splash_title3), stringResource(R.string.splash_desc3)),
        OnboardingPage(R.drawable.splash3_2x, stringResource(R.string.splash_title4), stringResource(R.string.splash_desc4)),
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
                modifier = Modifier.fillMaxWidth(),

            ) { page ->
                OnboardingItem(pages[page], pages, viewModel, navController)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth().padding(top = 26.dp, bottom = 30.dp)
            ) {
                repeat(pages.size) { index ->
                    val isSelected = state.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                if (isSelected) colorResource(R.color.select_dot)
                                else colorResource(R.color.unselected_dot)
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
                modifier = Modifier.width(309.dp).height(48.dp).align(Alignment.CenterHorizontally)
                    .padding(start = 15.dp, end = 15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.splash_button)
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
    }

}


@Composable
fun OnboardingItem(page : OnboardingPage, pages : List<OnboardingPage>, viewModel: OnboardingViewModel, navController: NavController) {
    val state = viewModel.state
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 44.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Image(
                painter = painterResource(id = page.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(horizontal = 20.dp).clip(RoundedCornerShape(20.dp)).width(310.dp).height(453.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = page.title, fontSize = 24.sp, textAlign = TextAlign.Center, fontFamily = FontFamily(
                Font(R.font.inter_bold)), fontWeight = FontWeight.W700, color = colorResource(R.color.title_splash_color))
            Text(text = page.desc, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 20.dp), fontFamily = FontFamily(Font(
                R.font.inter_medium)), color = colorResource(R.color.title_splash_color), fontWeight = FontWeight.W400)
        }
    }
}
@Preview
@Composable
fun PreviewOnboardingScreenMVI() {
    //OnboardingScreenMVI()
}