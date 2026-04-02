package com.example.composetutorial.mainUI.home

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.helper.SortType
import com.example.composetutorial.model.PDFFile
import com.example.composetutorial.model.Sort
import com.example.composetutorial.navigation.bottomHomeNav
import com.example.composetutorial.navigation.starredNav
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val state by homeViewModel.selectedTab.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        permissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listOf(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.READ_MEDIA_VIDEO,
                    android.Manifest.permission.READ_MEDIA_AUDIO,
                )
            } else {
                listOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
    )
    val bottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true,
    )
    val detailBottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
    )
    val sortBottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()

    }

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (permissionState.allPermissionsGranted && homeViewModel.pdfLists.value.isEmpty()) {
            homeViewModel.loadPDFFiles(context)
        }
    }

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
                                if (state.selectedTab == "All") colorResource(R.color.cyan) else Color.Transparent
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
                            navController.navigate(starredNav)
                            homeViewModel.handleIntent(HomeIntent.StarredTabClicked)
                        }, fontWeight = if(state.selectedTab == "Starred") FontWeight.Bold else FontWeight.Normal)

                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(40.dp)
                            .padding(start = 20.dp)
                            .background(
                                if (state.selectedTab == "Starred") colorResource(R.color.cyan) else Color.Transparent
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

                Image(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 10.dp, top = 15.dp, start = 5.dp).clickable {
                        homeViewModel.handleIntent(HomeIntent.SetShowSortBottom)
                    }
                )
            }

            if(state.setVertical) PDFListVertical(homeViewModel) else PDFListHorizontal(homeViewModel)
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
fun DetailBottomSheetContent(pdfFile: PDFFile) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)).fillMaxWidth().height(350.dp).background(color = Color.White).padding(15.dp)
    ) {
        Text(text = "Details", fontSize = 22.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))

        Column(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_details),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = stringResource(R.string.file_name), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)
                    Text(text = pdfFile.text, modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 13.sp, color = colorResource(R.color.detail_color))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_path),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = stringResource(R.string.path), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)
                    Text(text = pdfFile.path, modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 13.sp, color = colorResource(R.color.detail_color))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = stringResource(R.string.last_modified), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)
                    Text(text = pdfFile.date, modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 13.sp, color = colorResource(R.color.detail_color))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_size),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(text = stringResource(R.string.size), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)
                    Text(text = (pdfFile.fileSize.toFloat() / 1024.toFloat()).toString() + " KB", modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 13.sp, color = colorResource(R.color.detail_color))
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(pdfFile: PDFFile, onDetailClick: (PDFFile) -> Unit, onRenameClick: (PDFFile) -> Unit, onDeleteClick: (PDFFile) -> Unit) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)).fillMaxWidth().height(300.dp).background(color = Color.White).padding(15.dp)
    ) {
        PDFVerticalBottomItem(pdfFile)
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 15.dp, start = 5.dp).clickable {
                    onRenameClick(pdfFile)
                },
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Text(text = stringResource(R.string.rename), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp).clickable{
                    onDetailClick(pdfFile)
                },
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_details),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Text(text = stringResource(R.string.details), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Text(text = stringResource(R.string.share), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)

            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 5.dp).clickable {
                  onDeleteClick(pdfFile)
                },
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_delete),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(25.dp)
                )

                Text(text = stringResource(R.string.delete), modifier = Modifier.padding(top = 5.dp, start = 20.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), fontSize = 15.sp)

            }
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
                    Text(text = pdfFile.text, fontSize = 7.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 5.dp))
                    Text(text = pdfFile.date, fontSize = 7.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 4.dp),color = colorResource(R.color.gray))
                }
                Image(
                    painter = painterResource(R.drawable.ic_choose),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 30.dp, end = 5.dp)
                        .size(15.dp)
                        .clickable {
                            homeViewModel.handleIntent(HomeIntent.SetShowBottom(pdfFile))
                        }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarredScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val state by homeViewModel.selectedTab.collectAsState()
    val scope = rememberCoroutineScope()
    val sortBottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
    )
    val detailBottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
    )
    val bottomSheetState = rememberModalBottomSheetState(
        confirmValueChange = {true},
        skipPartiallyExpanded = true
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
            Text(text = "PDF Manager", fontSize = 30.sp, modifier = Modifier.align(alignment = Alignment.TopStart).padding(start = 20.dp, top = 50.dp), color = Color.White)
            Image(
                painter = painterResource(R.drawable.ic_diamond),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 50.dp, end = 20.dp).size(30.dp).align(alignment = Alignment.TopEnd)
            )
            //Spacer(modifier = Modifier.height(50.dp))

            var text by remember{ mutableStateOf("") }

            TextField(
                value = text,
                onValueChange = {text = it},
                placeholder = {Text(stringResource(R.string.search_text))},
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
                    unfocusedPlaceholderColor = colorResource(R.color.gray_thin),
                    focusedPlaceholderColor = colorResource(R.color.gray_thin)
                )
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)).background(color = Color.White).weight(1f).align(alignment = Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
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
                                if (state.selectedTab == "All") colorResource(R.color.cyan) else Color.Transparent
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
                            navController.navigate(starredNav)
                            homeViewModel.handleIntent(HomeIntent.StarredTabClicked)
                        }, fontWeight = if(state.selectedTab == "Starred") FontWeight.Bold else FontWeight.Normal)

                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .width(40.dp)
                            .padding(start = 20.dp)
                            .background(
                                if (state.selectedTab == "Starred") colorResource(R.color.cyan) else Color.Transparent
                            )
                    )

                }

                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(if(state.setVerticalStar) R.drawable.ic_list_ver else R.drawable.ic_list),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(end = 10.dp, top = 15.dp)
                        .clickable {
                            if(state.setVerticalStar) {
                                homeViewModel.handleIntent(HomeIntent.SetHorizontalStarClicked)
                            } else {
                                homeViewModel.handleIntent(HomeIntent.SetVerticalStarClicked)
                            }
                        }
                )
                Image(
                    painter = painterResource(R.drawable.ic_filter),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 10.dp, top = 15.dp, start = 5.dp).clickable {
                        homeViewModel.handleIntent(HomeIntent.SetShowSortBottom)
                    }
                )
            }
            Box(modifier = Modifier.weight(1f).padding(top = 5.dp)) {
                if(state.setVerticalStar) PDFListVerticalStarred(homeViewModel) else PDFListHorizontalStarred(homeViewModel)
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
fun PDFListHorizontalStarred(homeViewModel: HomeViewModel) {
    val pdfList by homeViewModel.pdfLists.collectAsState()
    val pdfListStarred : MutableList<PDFFile> = ArrayList()
    pdfList.forEach { pdf ->
        if(pdf.isStarred) {
            pdfListStarred.add(pdf)
        }
    }
    LazyVerticalGrid(
        columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        items(pdfListStarred) {
                pdf ->
            PDFItem(
                pdfFile = pdf,
                homeViewModel = homeViewModel
            )
        }
    }
}
@Composable
fun PDFListVerticalStarred(homeViewModel: HomeViewModel) {
    val pdfList by homeViewModel.pdfLists.collectAsState()
    val pdfListStarred : MutableList<PDFFile> = ArrayList()
    pdfList.forEach { pdf ->
        if(pdf.isStarred) {
            pdfListStarred.add(pdf)
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        items(pdfListStarred) {
            pdf -> PDFVerticalItem(
                pdfFile = pdf,
                homeViewModel = homeViewModel
            )
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
                Text(text = pdfFile.text, fontSize = 11.sp,modifier = Modifier.padding(start = 10.dp, top = 5.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))
                Text(text = pdfFile.date, fontSize = 11.sp,modifier = Modifier.padding(start = 10.dp, top = 7.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), color = colorResource(R.color.gray))
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
                    .clickable {
                        homeViewModel.handleIntent(HomeIntent.SetShowBottom(pdfFile))
                    }

            )
        }
    }
}
@Composable
fun PDFVerticalBottomItem(pdfFile: PDFFile) {
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
                Text(text = pdfFile.text, fontSize = 11.sp,modifier = Modifier.padding(start = 10.dp, top = 5.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))
                Text(text = pdfFile.date, fontSize = 11.sp,modifier = Modifier.padding(start = 10.dp, top = 7.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), color = colorResource(R.color.gray))
            }
        }
    }
}

@Composable
fun RenameDialog(pdfFile: PDFFile, homeViewModel: HomeViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current

    var text by remember {
        mutableStateOf(
            pdfFile.text.removeSuffix(".pdf")
        )
    }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(260.dp).width(400.dp).padding(20.dp).align(alignment = Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(R.string.rename), fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp, start = 15.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))

                TextField(
                    value = text,
                    onValueChange = {text = it},
                    placeholder = {Text("Rename")},
                    singleLine = true,
                    modifier = Modifier.padding(start = 15.dp, top = 25.dp, end = 15.dp).fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = colorResource(R.color.gray_edit),
                        unfocusedContainerColor = colorResource(R.color.gray_edit),
                        unfocusedTextColor = colorResource(R.color.cancel),
                        focusedTextColor = colorResource(R.color.cancel),
                        unfocusedPlaceholderColor = colorResource(R.color.text_edit)
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        modifier = Modifier.height(65.dp).width(160.dp).padding(top = 20.dp, start = 15.dp),
                        onClick = {onDismiss()},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.gray_edit)
                        )
                    ) {
                        Text(text = "CANCEL", fontSize = 18.sp, color = colorResource(R.color.cancel))
                    }
                    Button(
                        modifier = Modifier.height(65.dp).width(160.dp).padding(top = 20.dp, end= 15.dp),
                        onClick = {
                            if(text.isNotEmpty()) {
                                homeViewModel.renamePDF(context, pdfFile, text)

                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.button_edit)
                        )
                    ) {
                        Text(text = "OK", fontSize = 18.sp, color = colorResource(R.color.white))
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(pdfFile: PDFFile, homeViewModel: HomeViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.height(260.dp).width(400.dp).padding(20.dp).align(alignment = Alignment.Center),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(R.string.confirm_delete), fontSize = 20.sp, modifier = Modifier.padding(top = 20.dp, start = 15.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))

                Text(text = stringResource(R.string.file_delete), fontSize = 16.sp, modifier = Modifier.padding(start = 15.dp, top = 25.dp, end = 15.dp), fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        modifier = Modifier.height(65.dp).width(160.dp).padding(top = 20.dp, start = 15.dp),
                        onClick = {onDismiss()},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.gray_edit)
                        )
                    ) {
                        Text(text = "CANCEL", fontSize = 18.sp, color = colorResource(R.color.cancel))
                    }
                    Button(
                        modifier = Modifier.height(65.dp).width(160.dp).padding(top = 20.dp, end= 15.dp),
                        onClick = {
                            homeViewModel.deletePDF(context, pdfFile)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.button_edit)
                        )
                    ) {
                        Text(text = "DELETE", fontSize = 18.sp, color = colorResource(R.color.white))
                    }
                }
            }
        }
    }
}

@Composable
fun SortPDFBottom(selectedSort: Sort?, onSelected: (Sort) -> Unit, onDismiss: () -> Unit, homeViewModel: HomeViewModel) {
    val pdfList by homeViewModel.pdfLists.collectAsState()
    val state by homeViewModel.selectedTab.collectAsState()
    val pdfListStarred : MutableList<PDFFile> = ArrayList()
    pdfList.forEach { pdf ->
        if(pdf.isStarred) {
            pdfListStarred.add(pdf)
        }
    }
    val sortItemsList = listOf(
        Sort(1,R.drawable.new_to_old, stringResource(R.string.sort1_up_text), stringResource(R.string.sort1_down_text)),
        Sort(2, R.drawable.old_to_new, stringResource(R.string.sort2_up_text), stringResource(R.string.sort2_down_text)),
        Sort(3, R.drawable.a_z, stringResource(R.string.sort3_up_text), stringResource(R.string.sort3_down_text)),
        Sort(4, R.drawable.z_a, stringResource(R.string.sort4_up_text), stringResource(R.string.sort4_down_text)),
        Sort(5, R.drawable.large_to_small, stringResource(R.string.sort5_up_text), stringResource(R.string.sort5_down_text)),
        Sort(6, R.drawable.small_to_large, stringResource(R.string.sort6_up_text), stringResource(R.string.sort6_down_text))
    )
    Column(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)).fillMaxWidth().height(600.dp).background(color = Color.White).padding(15.dp)
    ) {
        Text(text = "Sort by", fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(sortItemsList) {
                sort -> SortPDFItem(
                    sort = sort,
                    isSelected = selectedSort == sort,
                    onSelected = onSelected
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 10.dp).padding(top = 20.dp),
            onClick = {
                when(selectedSort?.id) {
                    1 -> homeViewModel.sortPDF(SortType.DATE_NEW_TO_OLD)
                    2 -> homeViewModel.sortPDF(SortType.DATE_OLD_TO_NEW)
                    3 -> homeViewModel.sortPDF(SortType.NAME_AZ)
                    4 -> homeViewModel.sortPDF(SortType.NAME_ZA)
                    5 -> homeViewModel.sortPDF(SortType.FILE_SIZE_LARGE_TO_SMALL)
                    6 -> homeViewModel.sortPDF(SortType.FILE_SIZE_SMALL_TO_LARGE)
                }
                onDismiss()
            },
            colors = ButtonDefaults.buttonColors(
                Color(0XFF0085F2)
            )
        ) {
            Text(text = "DONE", color = Color.White)
        }
    }
}
@Composable
fun SortPDFItem(sort: Sort, isSelected : Boolean, onSelected: (Sort) -> Unit) {
    Row(
        modifier = Modifier.height(60.dp).fillMaxWidth().padding(horizontal = 10.dp).clip(RoundedCornerShape(20.dp)).background(color = colorResource(R.color.item_sort)).clickable{onSelected(sort)}
    ) {
        Image(
            painter = painterResource(sort.imgSrc),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.padding(top = 15.dp, start = 20.dp).size(30.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = sort.upText, fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 5.dp, start = 15.dp), color = colorResource(R.color.item_text_color1))
            Text(text = sort.downText, fontSize = 17.sp, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)), modifier = Modifier.padding(top = 5.dp, start = 15.dp), color = colorResource(R.color.item_text_color2))
        }
        RadioButton(selected = isSelected, onClick = {onSelected(sort)},colors = RadioButtonDefaults.colors(selectedColor = colorResource(R.color.cyan),unselectedColor = colorResource(R.color.gray_thin)), modifier = Modifier.padding(top = 5.dp, end = 20.dp))
    }
}
@Preview
@Composable
fun PreviewHomeScreen() {
    //SortPDFItem()
    //SortPDFBottom()
}