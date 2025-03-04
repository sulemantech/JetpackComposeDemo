package com.example.composeproject.activities

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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

@Composable
fun SearchScreen(
    navController: NavController,
    webViewModel: WebViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }
    var showAutoComplete by remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_left),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(10.dp)
                            .clickable { navController.popBackStack() }
                    )

                }

                Spacer(modifier = Modifier.width(8.dp))

                Text("Suchen", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.W500)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(37.dp)
                    .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hallo Niko Rangos",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(colorResource(id = R.color.text_black))
                            .clickable { }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Standort suchen & navi...",
                            color = colorResource(id = R.color.textColor),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { navController.navigate("search_route_screen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(39.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue))
                    ) {
                        Text("Wegbeschreibung", color = Color.Black, fontSize = 16.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            FloorMapWebView(url = "https://g8way-app.com/map/", webViewModel)
//
//        }

        BackHandler {
            if (webViewModel.webView?.canGoBack() == true) {
                webViewModel.webView?.goBack()
            } else {
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun ActionButton(text: String, iconRes: Int, width: Dp, buttonColor: Color, onClick: () -> Unit) {
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

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FloorMapWebView(url: String, webViewViewModel: WebViewModel) {
    val context = LocalContext.current

    val webView = webViewViewModel.webView ?: WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()
        loadUrl(url)
    }

    webViewViewModel.webView = webView

    AndroidView(
        factory = { webView },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchScreen() {
    SearchScreen(navController = rememberNavController())
}
