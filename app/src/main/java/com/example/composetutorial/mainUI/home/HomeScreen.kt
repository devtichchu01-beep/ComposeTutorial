package com.example.composetutorial.mainUI.home

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.toInt
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.model.PDFFile
import com.example.composetutorial.navigation.bottomHomeNav
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val state by homeViewModel.selectedTab.collectAsState()

    val context = LocalContext.current

    val permissionState = rememberMultiplePermissionsState(
        permissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
                )
            } else {
                listOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted) {
            homeViewModel.loadPDFFiles(context)
        }
    }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colorResource(R.color.blue_tran),
                        colorResource(R.color.purple_tran)
                    )
                )
            )
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        colorResource(R.color.blue_tran),
                        colorResource(R.color.purple_tran)
                    )
                )
            )) {
            Text(text = "PDF Manager", fontSize = 30.sp, modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .padding(start = 20.dp, top = 50.dp), color = Color.White)
            Image(
                painter = painterResource(R.drawable.ic_diamond),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 50.dp, end = 20.dp)
                    .size(30.dp)
                    .align(alignment = Alignment.TopEnd)
            )
            //Spacer(modifier = Modifier.height(50.dp))

            var text by remember{ mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = {text = it},
                placeholder = {Text("Search document..")},
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 120.dp)
                    .clip(RoundedCornerShape(30.dp)),
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
                    unfocusedPlaceholderColor = colorResource(R.color.gray_thin),
                    focusedPlaceholderColor = colorResource(R.color.gray_thin)
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .weight(1f)
                .background(color = Color.White)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Row(

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "All", modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp)
                        .clickable {
                            navController.navigate(bottomHomeNav)
                            homeViewModel.handleIntent(HomeIntent.AllTabClicked)
                        }, fontWeight = if(state.selectedTab == "All") FontWeight.Bold else FontWeight.Normal)

                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(40.dp)
                            .padding(start = 20.dp)
                            .background(
                                if (state.selectedTab == "All") Color(0xFF0485F8) else Color.Transparent
                            )
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Starred", modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp)
                        .clickable {
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
                                if (state.selectedTab == "Starred") Color(0xFF0485F8) else Color.Transparent
                            )
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(if(state.setVertical) R.drawable.ic_list_ver else R.drawable.ic_list),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 10.dp, top = 15.dp)
                        .clickable {
                            if(state.setVertical) {
                                homeViewModel.handleIntent(HomeIntent.SetHorizontalClicked)
                            } else {
                                homeViewModel.handleIntent(HomeIntent.SetVerticalClicked)
                            }
                        }
                )
                Spacer(modifier = Modifier.width(5.dp))

                Image(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 10.dp, top = 15.dp)
                )
            }

            if(state.setVertical) PDFListVertical(homeViewModel) else PDFListHorizontal(homeViewModel)
        }
    }

//    ModalBottomSheet(
////        modifier = Modifier.background(color = Color.White),
//        onDismissRequest = {scope.launch{bottomSheetState.hide()}}
//    ) {
//
//    }
}
@Composable
fun BottomSheetContent() {
    val pdfFile =  PDFFile(1, R.drawable.pdf_img,  R.drawable.type_pdf, "Practical UI Free Preview", "10:11 02/03/26",1, "assda")
    val homeViewModel : HomeViewModel = viewModel()
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)).fillMaxWidth().height(500.dp).background(color = Color.White).padding(15.dp)
    ) {
        PDFVerticalItem(pdfFile, homeViewModel)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
//            Image(
//                painter = painterResource(),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.height(30.dp).width(30.dp).
//            )
        }
    }
}
@Composable
fun PDFListHorizontal(homeViewModel: HomeViewModel) {
    val pdfLists by homeViewModel.pdfLists.collectAsState()

    LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pdfLists) { pdf ->
            PDFItem(
                pdfFile = pdf,
                homeViewModel = homeViewModel
            )
        }
    }
}
@Composable
fun PDFListVertical(homeViewModel: HomeViewModel) {
    val pdfLists by homeViewModel.pdfLists.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(pdfLists) {
            pdf -> PDFVerticalItem(
                pdfFile = pdf,
                homeViewModel = homeViewModel
            )
        }
    }
}

fun getPDFFiles(context : Context): List<PDFFile> {
    val pdfList = mutableListOf<PDFFile>()
    val uri =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Files.getContentUri(
            MediaStore.VOLUME_EXTERNAL
        )
    } else {
        MediaStore.Files.getContentUri("external")
    }

    val projection = arrayOf(
        MediaStore.Files.FileColumns._ID,
        MediaStore.Files.FileColumns.DISPLAY_NAME,
        MediaStore.Files.FileColumns.SIZE,
//        MediaStore.Files.FileColumns.DATA,
        MediaStore.Files.FileColumns.MIME_TYPE,
        MediaStore.Files.FileColumns.DATE_MODIFIED
    )
    val selection =
        "LOWER(${MediaStore.Files.FileColumns.DISPLAY_NAME}) LIKE ?"

    val selectionArgs =
        arrayOf("%.pdf")
    val cursor = context.contentResolver.query(
        uri,
        projection,
        selection,
        selectionArgs,
        null
    )

    cursor?.use {
        val idCol = (
            it.getColumnIndex(
                MediaStore.Files.FileColumns._ID
            )
        )
        val nameCol = (
            it.getColumnIndex(
                MediaStore.Files.FileColumns.DISPLAY_NAME
            )
        )
        val sizeCol = (
            it.getColumnIndex(
                MediaStore.Files.FileColumns.SIZE
            )
        )
//        val pathCol = (
//            it.getColumnIndexOrThrow(
//                MediaStore.Files.FileColumns.DATA
//            )
//        )
        val dateCol = (
            it.getColumnIndex(
                MediaStore.Files.FileColumns.DATE_MODIFIED
            )
        )
        while(it.moveToNext()) {
            val id = it.getLong(idCol)
            val name = it.getString(nameCol)
            val size = it.getLong(sizeCol)
//            val path = it.getString(pathCol)
            val dateLong = it.getLong(dateCol) * 1000

            val contentUri =
                ContentUris.withAppendedId(uri, id)
            val date = SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
            ).format(Date(dateLong))


            pdfList.add(
                PDFFile(
                    id = id,
                    imgSource = R.drawable.pdf_img,
                    fileType = R.drawable.type_pdf,
                    text = name,
                    date = date,
                    fileSize = size,
                    path = contentUri.toString()
                )
            )

        }
    }
    return pdfList
}
@Composable
fun PDFItem(pdfFile: PDFFile, homeViewModel: HomeViewModel) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .width(120.dp)
            .height(180.dp)
            .background(color = colorResource(R.color.light_gray))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(pdfFile.imgSource),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 12.dp)
                    .padding(top = 10.dp)
                    .clip(
                        RoundedCornerShape(topEnd = 50.dp)
                    )
            )
            Image(
                painter = if(!pdfFile.isStarred) painterResource(R.drawable.ic_star) else painterResource(R.drawable.ic_starred),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(25.dp)
                    .align(alignment = Alignment.TopEnd)
                    .padding(top = 5.dp, end = 5.dp)
                    .clickable {
                        homeViewModel.handleIntent(HomeIntent.ToggleStar(pdfFile))
                    }
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(pdfFile.fileType),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 10.dp)
                        .size(15.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = pdfFile.text, fontSize = 8.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 5.dp))
                    Text(text = pdfFile.date, fontSize = 8.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 5.dp),color = colorResource(R.color.gray))
                }
                Image(
                    painter = painterResource(R.drawable.ic_choose),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 30.dp, end = 5.dp)
                        .size(15.dp)
                        .clickable {

                        }
                )
            }
        }
    }
}

@Composable
fun PDFVerticalItem(pdfFile: PDFFile, homeViewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(color = colorResource(R.color.white))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Box() {
                Image(
                    painter = painterResource(pdfFile.imgSource),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(40.dp).border(width = 1.dp, color = colorResource(R.color.gray_thin))
                )
                Column() {
                    Image(
                        painter = painterResource(pdfFile.fileType),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 35.dp, start = 1.dp)
                            .size(15.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = pdfFile.text, modifier = Modifier.padding(start = 10.dp, top = 5.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))
                Text(text = pdfFile.date, modifier = Modifier.padding(start = 10.dp, top = 7.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), color = colorResource(R.color.gray))
            }
            Image(
                painter = if(!pdfFile.isStarred) painterResource(R.drawable.ic_star) else painterResource(R.drawable.ic_starred),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(18.dp)
                    .clickable {
                        homeViewModel.handleIntent(HomeIntent.ToggleStar(pdfFile))
                    }
            )
            Image(
                painter = painterResource(R.drawable.ic_choose),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 5.dp, start = 5.dp, top = 10.dp)
                    .size(18.dp)

            )
        }
    }
}
@Preview
@Composable
fun PreviewHomeScreen() {
    BottomSheetContent()
}