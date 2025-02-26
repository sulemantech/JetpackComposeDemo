package com.example.composeproject

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SearchRouteScreen(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_left),
                    contentDescription = stringResource(id = R.string.back_icon_desc),
                    tint = Color.White,
                    modifier = Modifier.size(10.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text( text = stringResource(id = R.string.suchen), color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.W500)

        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                .padding(12.dp),
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
                .height(140.dp)
                .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var startPoint by remember { mutableStateOf("") }
                var destination by remember { mutableStateOf("") }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextField(
                        value = startPoint,
                        onValueChange = { startPoint = it },
                        placeholder = {
                            Text(
                                text = "Grobraum-büro",
                                color = colorResource(id = R.color.textColor),
                                fontSize = 16.sp
                            )
                        },
                        modifier = Modifier
                            .width(264.dp)
                            .background(
                                colorResource(id = R.color.background),
                                RoundedCornerShape(8.dp)
                            ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Black,
                            focusedContainerColor = Color.Black,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { startPoint = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear text",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }



                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TextField(
                        value = startPoint,
                        onValueChange = { startPoint = it },
                        placeholder = {
                            Text(
                                text = "Eingang West",
                                color = colorResource(id = R.color.textColor),
                                fontSize = 16.sp
                            )
                        },
                        modifier = Modifier
                            .width(264.dp)
                            .background(
                                colorResource(id = R.color.background),
                                RoundedCornerShape(8.dp)
                            ),
                        textStyle = LocalTextStyle.current.copy(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Black,
                            focusedContainerColor = Color.Black,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { startPoint = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear text",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            FloorMapWebView1(url = "https://g8way-app.com/map/")
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun FloorMapWebView1(url: String) {
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchRouteScreen() {
  SearchRouteScreen(navController = rememberNavController())
}

