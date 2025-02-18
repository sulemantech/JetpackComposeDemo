package com.example.composeproject

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    val view = LocalView.current

    SideEffect {
        val window = (view.context as? android.app.Activity)?.window
        window?.statusBarColor = android.graphics.Color.BLACK
        WindowInsetsControllerCompat(window!!, window.decorView).isAppearanceLightStatusBars = false
    }

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
                text = "Meine Flüge",
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
            CategoryButton("Geschäfte")
            CategoryButton("Gates")
            CategoryButton("Lounge")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.weight(1f)) {
            AndroidView(
                factory = {
                    webView.apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        webChromeClient = WebChromeClient()
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            cacheMode = WebSettings.LOAD_DEFAULT
                            builtInZoomControls = false
                        }
                        loadUrl("https://g8way-app.com/map/")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
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
fun NoTicketsAvailable(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
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
                text = "Keine Tickets verfügbar",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Tippe auf das Kreuz oben, um ein Ticket zu importieren",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun CategoryButton(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 14.sp,
        modifier = Modifier
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { }
    )
}

//@Composable
//fun BottomNavigationBar() {
//    NavigationBar(containerColor = Color.DarkGray) {
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
//            label = { Text("Home", color = Color.White) },
//            selected = true,
//            onClick = {}
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
//            label = { Text("Search", color = Color.White) },
//            selected = false,
//            onClick = {}
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
//            label = { Text("Settings", color = Color.White) },
//            selected = false,
//            onClick = {}
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(navController)
}

