package com.example.composeproject.activities

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchRouteScreen(navController: NavController) {
    var startPoint by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    val locations = listOf("Großraumbüro", "Eingang West", "Meeting Room", "Lobby")

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
                    .background(colorResource(id = R.color.btnColor), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hallo Niko Rangos",
                    color = colorResource(id = R.color.btn_text_field),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
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

            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        IconButton(onClick = { /* Right arrow action */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_location),
                                contentDescription = "Right Arrow",
                                tint = Color.White
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AutoCompleteTextField(
                            label = "Grobraum-büro",
                            text = startPoint,
                            onTextChange = { startPoint = it },
                            suggestions = locations
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        AutoCompleteTextField(
                            label = "Eingang West/Wartebe",
                            text = destination,
                            onTextChange = { destination = it },
                            suggestions = locations
                        )
                    }

                    IconButton(onClick = {
                        val temp = startPoint
                        startPoint = destination
                        destination = temp
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_swap),
                            contentDescription = "Swap Locations",
                            tint = Color.White
                        )
                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            FloorMapWebView1(url = "https://g8way-app.com/map/")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    suggestions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val filteredSuggestions = suggestions.filter { it.contains(text, ignoreCase = true) }
    val isSelected = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth(),
      //  contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            TextField(
                value = text,
                onValueChange = {
                    onTextChange(it)
                    expanded = true
                    isSelected.value = false
                },
                placeholder = {
                    Text(
                        text = label,
                        color = colorResource(id = R.color.btn_text_field),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .width(280.dp)
                    .menuAnchor(),
                textStyle = LocalTextStyle.current.copy(
                    color = if (isSelected.value) colorResource(id = R.color.btn_text_field) else Color.White,
                    fontSize = 16.sp
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = colorResource(id = R.color.text_black),
                    focusedContainerColor = colorResource(id = R.color.text_black),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    IconButton(onClick = { onTextChange(""); isSelected.value = false }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear text",
                            tint = Color.White,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            ExposedDropdownMenu(
                expanded = expanded && filteredSuggestions.isNotEmpty(),
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .padding(top = 4.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                filteredSuggestions.forEach { suggestion ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                suggestion,
                                color = Color.Black,
                                fontSize = 16.sp
                            )
                        },
                        onClick = {
                            onTextChange(suggestion)
                            isSelected.value = true
                            expanded = false
                        }
                    )
                }
            }
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

