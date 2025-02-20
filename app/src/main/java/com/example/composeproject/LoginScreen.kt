package com.example.composeproject

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.regex.Pattern

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }


    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_light)
    )

    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp)
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = "Image from resources",
                modifier = Modifier.size(147.dp,39.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Login",
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )


            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "E-Mail",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                fun isValidEmail(email: String): Boolean {
                    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = true
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_message),
                            contentDescription = "Email Icon",
                            tint = Color.White
                        )
                    },
                    placeholder = { Text("E-Mail", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.blue), // Blue when focused
                        unfocusedBorderColor = when {
                            isFocused -> colorResource(id = R.color.blue)
                            isEmailValid -> Color.Gray
                            else -> Color.Red
                        },
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    isError = !isEmailValid,
                    textStyle = TextStyle(color = Color.White)
                )

                if (!isEmailValid) {
                    Text(
                        text = "Diese E-Mail ist nicht registriert.",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        isEmailValid = isValidEmail(email.text)
                        if (isEmailValid) {
                            navController.navigate("EmailVerificationScreen")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (email.text.isNotEmpty()) Color(0xFF00FFFF) else colorResource(id = R.color.blue)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Login mit Magiclink",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }



//                Button(
//                    onClick = {
//                        isEmailValid = isValidEmail(email.text)
//                        if (isEmailValid) {
//                            navController.navigate("EmailVerificationScreen")
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(56.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (isEmailEntered) Color(0xFF00FFFF) else Color(0xFF008B8B)
//                    ),
//                    shape = RoundedCornerShape(12.dp)
//                ) {
//                    Text(
//                        "Login mit Magiclink",
//                        color = Color.Black,
//                        fontSize = 16.sp,
//                        fontFamily = robotoFontFamily,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//
            }

            Text(
                buildAnnotatedString {
                    append("Noch kein Konto? ")
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.blue), textDecoration = TextDecoration.Underline)) {
                        append("Registrieren")
                    }
                },
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("registerScreen")
                }
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    val emailPattern = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    )
    return emailPattern.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}
