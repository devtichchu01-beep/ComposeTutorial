package com.example.composetutorial.mainUI.recent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.mainUI.home.HomeIntent
import com.example.composetutorial.mainUI.home.HomeViewModel

@Composable
fun RecentScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val state by homeViewModel.selectedTab.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))
                )
            )) {
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.horizontalGradient(colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))))) {
            Text(text = "PDF Manager", fontSize = 30.sp, modifier = Modifier.align(alignment = Alignment.TopStart).padding(start = 20.dp, top = 50.dp), color = Color.White)
            Image(
                painter = painterResource(R.drawable.ic_diamond),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 50.dp, end = 20.dp).height(30.dp).width(30.dp).align(alignment = Alignment.TopEnd)
            )
            //Spacer(modifier = Modifier.height(50.dp))

            var text by remember{ mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = {text = it},
                placeholder = {Text("Search document..")},
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).padding(top = 120.dp).clip(RoundedCornerShape(30.dp)),
                leadingIcon = {
                    Icon (
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color(0xFFD3D3D3)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedPlaceholderColor = Color(0xFFD3D3D3),
                    focusedPlaceholderColor = Color(0xFFD3D3D3)
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)).background(color = Color.White).height(530.dp).align(alignment = Alignment.BottomCenter)
            ) {
                Row() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "All", modifier = Modifier.padding(top = 20.dp, start = 20.dp).clickable {
                            homeViewModel.handleIntent(HomeIntent.AllTabClicked)
                        }, fontWeight = if(state.selectedTab == "All") FontWeight.Bold else FontWeight.Normal)

                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .width(40.dp)
                                .padding(start = 20.dp)
                                .background(
                                    if (state.selectedTab == "All") Color(0xFF0485F8) else Color.Transparent)
                        )

                    }
                    Spacer(modifier = Modifier.width(20.dp))

//                    Text(text = "Starred", fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp).clickable{
//                        navController.navigate("recent")
//                    })
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Starred", modifier = Modifier.padding(top = 20.dp, start = 20.dp).clickable {
                            navController.navigate("recent")
                            homeViewModel.handleIntent(HomeIntent.StarredTabClicked)
                        }, fontWeight = if(state.selectedTab == "Starred") FontWeight.Bold else FontWeight.Normal)

                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .width(40.dp)
                                .padding(start = 20.dp)
                                .background(
                                    if (state.selectedTab == "Starred") Color(0xFF0485F8) else Color.Transparent)
                        )

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(R.drawable.ic_list),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(end = 10.dp, top = 15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Image(
                        painter = painterResource(R.drawable.ic_filter),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(end = 10.dp, top = 15.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    //RecentScreen()
}