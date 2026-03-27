package com.example.composetutorial.mainUI.setting

import android.R.attr.end
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composetutorial.R
import com.example.composetutorial.mainUI.language.LanguageViewModel
import com.example.composetutorial.model.Setting

@Composable
fun SettingScreen(languageViewModel: LanguageViewModel, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = listOf(Color(0xFF6498F1), Color(0xFF7D61FF))))) {
        val context = LocalContext.current
        val languageState by languageViewModel.state.collectAsState()
        var selected by remember {mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth().height(80.dp)) {
            Text(text = "Settings", modifier = Modifier.align(Alignment.TopStart).padding(top = 45.dp, start = 25.dp), fontSize = 25.sp, color = Color.White, fontFamily = FontFamily(Font(R.font.inter_28pt_regular)))
        }
        SettingList(onSelected = {
            setting ->
                if(setting.name == "Language") {
                    navController.navigate("language")
                } else {
//                    navController.navigate("onboarding")
                }
        }, modifier = Modifier.weight(1f), selectedLanguage = languageState.selectedLanguage?.name ?: "English")
    }
}

@Composable
fun SettingList(onSelected: (Setting) -> Unit, modifier: Modifier = Modifier, selectedLanguage : String) {
    val settings = listOf(
        Setting(R.drawable.ic_change, "Premium features\nGet unlimited access today"),
        Setting(R.drawable.ic_language, "Language"),
        Setting(R.drawable.ic_rate, "Rate us"),
        Setting(R.drawable.ic_use, "Terms of use"),
        Setting(R.drawable.ic_share, "Share app")
    )

    var selectedSetting by remember{mutableStateOf<Setting?>(null)}
    LazyColumn(
        modifier = modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(settings) { sett ->
            SettingItem(
                setting = sett,
                isSelected = selectedSetting == sett,
                onSelected = {
                    selectedSetting = it
                    onSelected(it)
                },
                selectedLanguage = selectedLanguage
            )
        }
    }
}

@Composable
fun SettingItem(setting: Setting, isSelected: Boolean, onSelected: (Setting) -> Unit, selectedLanguage: String) {
    Row(modifier = Modifier.fillMaxWidth().height(70.dp).background(color = colorResource(R.color.white),
        RoundedCornerShape(20.dp)).clickable{onSelected (setting)}.padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(setting.symbol),
            contentDescription = null,
            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = setting.name, fontSize = 16.sp, modifier = Modifier.weight(1f))

        if(setting.name == "Language") {
            Text(text = selectedLanguage, color = Color(0xFF0485F8), fontSize = 16.sp, modifier = Modifier.padding(end= 10.dp))
            Image(
                painter = painterResource(R.drawable.ic_next),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(20.dp).width(20.dp).clip(RoundedCornerShape(10.dp))
            )
        }
    }
}
@Preview
@Composable
fun PreviewSettingScreen() {
//    SettingScreen()
}

