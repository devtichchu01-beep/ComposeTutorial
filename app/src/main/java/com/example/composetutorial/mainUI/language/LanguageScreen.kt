package com.example.composetutorial.mainUI.language

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.model.Language

@Composable
fun LanguageScreen(viewModel: LanguageViewModel,navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))))) {
        val state by viewModel.state.collectAsState()
        val context = LocalContext.current;
        //var showSelected by remember {mutableStateOf(false)}

        Box(modifier = Modifier.fillMaxWidth().height(80.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.align(Alignment.TopStart)
                    .padding(top = 40.dp, start = 25.dp)
                    .size(28.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(text = "Language", modifier = Modifier.align(Alignment.TopCenter).padding(top = 40.dp, start = 20.dp), fontSize = 25.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.mplus_rounded1c_bold)))

            if(state.isSelected) {
                Image(
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.align(Alignment.TopEnd)
                        .padding(top = 42.dp, end = 20.dp)
                        .size(28.dp)
                        .clickable {
//                            (context as Activity).startActivity(Intent(context, MainActivity::class.java))
                            navController.navigate("setting")
                        }
                )
            }
        }

        LanguageList(
            selectedLanguage = state.selectedLanguage,
            onSelected = {viewModel.handleIntent(LanguageIntent.SelectedLanguage(it))},
            modifier = Modifier.weight(1f)
        )
//        LanguageList(onSelected = {
//            showSelected = true
//        }, modifier = Modifier.weight(1f))
    }
}

@Composable
fun LanguageList(selectedLanguage: Language?, onSelected: (Language) -> Unit, modifier: Modifier = Modifier) {
    val languages = listOf(
        Language("Arabic", R.drawable.arabic),
        Language("Bulgarian", R.drawable.bulgarian),
        Language("Hindi", R.drawable.hindi),
        Language("English", R.drawable.english),
        Language("Portuguese", R.drawable.portuguese),
        Language("Spanish", R.drawable.spanish),
        Language("Chinese", R.drawable.chinese),
        Language("Czech", R.drawable.czech),
        Language("Polish", R.drawable.polish),
        Language("Dutch", R.drawable.dutch),
        Language("France", R.drawable.france),
    )

    //var selectedLanguage by remember {mutableStateOf<Language?>(null)}

    LazyColumn(
        modifier = modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(languages) {
                lang -> LanguageItem(
            language = lang,
            isSelected = selectedLanguage == lang,
            onSelected = onSelected
            )
//            onSelected = {
//                selectedLanguage = it
//                onSelected(it)
//            })
        }
    }
}


@Composable
fun LanguageItem(language: Language, isSelected: Boolean, onSelected: (Language) -> Unit ) {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.White,
            RoundedCornerShape(20.dp)).clickable{onSelected (language)}.padding(horizontal = 16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = language.flag),
            contentDescription = null,
            modifier = Modifier.size(30.dp).clip(RoundedCornerShape(30.dp)).border(width = 1.dp, color = Color(0xFFD3D3D3), shape = CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = language.name, fontSize = 16.sp, modifier = Modifier.weight(1f))

        RadioButton(selected = isSelected, onClick = {onSelected(language)}, colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF0485F8),unselectedColor = Color(0xFFD3D3D3)))
    }
}
@Preview
@Composable
fun PreviewLanguageScreen() {
    //LanguageScreen()
}