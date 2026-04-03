package com.example.composetutorial.mainUI.recent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.mainUI.home.BottomSheetContent
import com.example.composetutorial.mainUI.home.DeleteDialog
import com.example.composetutorial.mainUI.home.DetailBottomSheetContent
import com.example.composetutorial.mainUI.home.HomeIntent
import com.example.composetutorial.mainUI.home.HomeViewModel
import com.example.composetutorial.mainUI.home.PDFItem
import com.example.composetutorial.mainUI.home.PDFListHorizontal
import com.example.composetutorial.mainUI.home.PDFListHorizontalStarred
import com.example.composetutorial.mainUI.home.RenameDialog
import com.example.composetutorial.mainUI.home.SortPDFBottom
import com.example.composetutorial.model.PDFFile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentScreen(/*navController: NavController,*/ homeViewModel: HomeViewModel) {
    val state by homeViewModel.selectedTab.collectAsState()
    val pdfList by homeViewModel.recentFiles.collectAsState()

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
    )
    val detailBottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true,
    )
    val sortBottomSheetState = rememberModalBottomSheetState (
        confirmValueChange = {true},
        skipPartiallyExpanded = true,
    )
    Column(
        modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(colorResource(R.color.blue_tran), colorResource(R.color.purple_tran))
            )
        ))
    {
        Box(modifier = Modifier.fillMaxWidth().background(brush = Brush.horizontalGradient(colors = listOf(colorResource(R.color.blue_tran), colorResource(R.color.purple_tran))))) {
            Text(text = "Recent", fontSize = 20.sp, modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(start = 16.dp, top = 44.dp), color = Color.White, fontFamily = FontFamily(
                Font(R.font.inter_medium)))
            Image(
                painter = painterResource(R.drawable.vector_diamond),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 50.dp, end = 20.dp).size(20.dp).align(alignment = Alignment.TopEnd)
            )

            var text by remember{ mutableStateOf("") }


            TextField(
                value = text,
                onValueChange = {
                    text = it
                    homeViewModel.searchQuery.value = it
                },
                placeholder = {Text("Search document..", fontSize = 12.sp, fontFamily = FontFamily(Font(R.font.inter)), fontWeight = FontWeight.W400)},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(138.dp)
                    .padding(horizontal = 15.dp)
                    .padding(top = 88.dp)
                    .clip(RoundedCornerShape(30.dp)),
                leadingIcon = {
                    Icon (
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = colorResource(R.color.gray_thin)
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedPlaceholderColor = colorResource(R.color.gray_thin),
                    focusedPlaceholderColor = colorResource(R.color.gray_thin)
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)).background(color = Color.White).weight(1f).align(alignment = Alignment.CenterHorizontally)
        ) {
            Box(modifier = Modifier.weight(1f).padding(top = 25.dp)) {
                PDFListRecent(homeViewModel)
            }
        }
    }
    val pdf = state.selectedPDF
    if(state.showBottom && pdf != null) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                    homeViewModel.handleIntent(HomeIntent.SetShowBottom(pdf))
                }
            }
        ) {
            BottomSheetContent(pdf,
                onDetailClick = { clickedPDF ->
                    homeViewModel.handleIntent(
                        HomeIntent.SetShowSecondBottom(clickedPDF)
                    )
                },
                onRenameClick = { clickedPDF ->
                    scope.launch {
                        homeViewModel.handleIntent(
                            HomeIntent.SetShowRenameDialog(clickedPDF)
                        )
                    }
                },
                onDeleteClick = { clickedPDF ->
                    scope.launch {
                        homeViewModel.handleIntent(
                            HomeIntent.SetShowDeleteDialog(clickedPDF)
                        )
                    }
                },
            )
        }
    }

    if(state.showSecondBottom && pdf != null) {
        ModalBottomSheet(
            sheetState = detailBottomSheetState,
            onDismissRequest = {
                scope.launch {
                    detailBottomSheetState.hide()
                    homeViewModel.handleIntent(HomeIntent.SetShowSecondBottom(pdf))
                }
            }
        ) {
            DetailBottomSheetContent(pdf)
        }
    }

    if(state.showRenameDialog && pdf != null) {
        RenameDialog(pdfFile = pdf, homeViewModel = homeViewModel, onDismiss = {
            homeViewModel.handleIntent(HomeIntent.SetShowRenameDialog(pdf))
        })
    }
    if(state.showDeleteDialog && pdf != null) {
        DeleteDialog(pdfFile = pdf, homeViewModel = homeViewModel, onDismiss = {
            homeViewModel.handleIntent(HomeIntent.SetShowDeleteDialog(pdf))
        })
    }
    if(state.showSortBottom) {
        ModalBottomSheet(
            sheetState = sortBottomSheetState,
            onDismissRequest =  {
                scope.launch {
                    sortBottomSheetState.hide()
                    homeViewModel.handleIntent(HomeIntent.SetShowSortBottom)
                }
            }
        ) {
            SortPDFBottom(
                selectedSort = state.selectedSort,
                onSelected = {
                    homeViewModel.handleIntent(HomeIntent.SelectedSort(it))
                },
                onDismiss = {
                    homeViewModel.handleIntent(HomeIntent.SetShowSortBottom)
                },
                homeViewModel = homeViewModel
            )
        }
    }
}

@Composable
fun PDFListRecent(homeViewModel: HomeViewModel) {
    val pdfList by homeViewModel.recentFiles.collectAsState()

    LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp))
    {
        items(pdfList) {
            pdf -> PDFItem(
                pdfFile = pdf,
                homeViewModel = homeViewModel
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    val homeViewModel : HomeViewModel = viewModel()
    RecentScreen(homeViewModel)
}