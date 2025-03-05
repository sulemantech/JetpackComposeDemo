package com.example.composeproject.activities

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.R
import com.example.composeproject.viewmodel.WebViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HomeScreen(
    navController: NavController,
    webViewViewModel: WebViewModel = viewModel()
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    val webView = webViewViewModel.webView ?: WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            builtInZoomControls = false
        }
        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()
        loadUrl("https://g8way-app.com/map/")
    }

    webViewViewModel.webView = webView

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.my_flights),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        NoTicketsAvailable(navController)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryButton(stringResource(id = R.string.category_shops), R.drawable.icon_shop)
            CategoryButton(stringResource(id = R.string.category_gates), R.drawable.icon_gate)
            CategoryButton(stringResource(id = R.string.category_lounge), R.drawable.ic_lounge)

        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            AndroidView(
                factory = { webView },
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UploadOption1("", R.drawable.icon_notification, 109.dp, colorResource(id = R.color.btnColor)) {}
                UploadOption1("", R.drawable.icon_direction, 109.dp, colorResource(id = R.color.blue)) {}
                UploadOption1("", R.drawable.icon_setting, 109.dp, colorResource(id = R.color.btnColor)) {}
            }
        }

        BackHandler {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun UploadOption1(text: String, iconRes: Int, width: Dp, buttonColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(buttonColor)
            .clickable { onClick() }
            .width(width)
            .height(45.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun NoTicketsAvailable(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(colorResource(id = R.color.btnColor), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable {
                navController.navigate("upload_ticket_screen")
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Ticket",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.no_tickets_available),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.tap_to_import_ticket),
                color = colorResource(id = R.color.textColor),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CategoryButton(text: String, iconRes: Int) {
    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.btnColor), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            modifier = Modifier
                .size(20.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = text,
            color = colorResource(id = R.color.btn_text_field),
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController)
}
