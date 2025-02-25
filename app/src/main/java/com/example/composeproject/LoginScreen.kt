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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

    val robotoFontFamily = FontFamily(Font(R.font.roboto_light))
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
                .verticalScroll(scrollState) // Enables scrolling
                .padding(bottom = WindowInsets.ime.asPaddingValues().calculateBottomPadding()) // Pushes up when keyboard opens
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = stringResource(id = R.string.image_from_resources),
                modifier = Modifier.size(147.dp, 39.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.login),
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 40.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.e_mail),
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = true
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_message),
                            contentDescription = stringResource(id = R.string.e_mail_icon),
                            tint = Color.White,
                            modifier = Modifier.padding(start = 14.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.e_mail),
                            color = Color.Gray,

                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.blue),
                        unfocusedBorderColor = when {
                            isFocused -> colorResource(id = R.color.blue)
                            isEmailValid -> colorResource(id = R.color.textColor1)
                            else -> Color.Red
                        },
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    isError = !isEmailValid,
                    textStyle = TextStyle(color = Color.White)
                )

                if (!isEmailValid) {
                    Text(
                        text = stringResource(id = R.string.diese_e_mail_ist_nicht_registriert_),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        isEmailValid = isValidEmail(email.text)
                        if (isEmailValid) {
                            navController.navigate("EmailVerificationScreen")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (email.text.isNotEmpty()) Color(0xFF00FFFF) else colorResource(id = R.color.teal_700
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login_mit_magiclink),
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            

            Text(
                buildAnnotatedString {
                    append(stringResource(id = R.string.no_account, ""))
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.blue),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(stringResource(id = R.string.register))
                    }
                },
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("search_screen")
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
