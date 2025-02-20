package com.example.composeproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.regex.Pattern

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var isPrivacyAccepted by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_light)
    )

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
                text = "Registrieren",
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Name",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Email Icon",
                            tint = Color.White
                        )
                    },
                    placeholder = { Text("Name", color = Color.Gray) },
                    modifier = Modifier.fillMaxWidth()
                    .focusRequester(nameFocusRequester),
                shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Cyan,
                        unfocusedBorderColor = Color.Gray,

                        cursorColor = colorResource(id = R.color.blue)

                    ),
                    textStyle = TextStyle(color = Color.White),
                            keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                            ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            emailFocusRequester.requestFocus()
                        }
                    )
                )
            }


            Column(modifier = Modifier.fillMaxWidth()) {
                Text("E-Mail", color = Color.White, fontSize = 14.sp,modifier = Modifier.padding(bottom = 8.dp))
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
                            isFocused -> colorResource(id = R.color.blue) // Blue when typing
                            isEmailValid -> Color.Gray // Gray when empty or valid
                            else -> Color.Red // Red when invalid
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

                Spacer(modifier = Modifier.height(6.dp))
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color.Cyan)
                )
                Text(
                    buildAnnotatedString {
                        append("Ich akzeptiere die ")
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.blue), textDecoration = TextDecoration.Underline)) {
                            append("Allgemeinen Geschäftsbedingungen")
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp) // Add 20dp margin between the two rows
            ) {
                Checkbox(
                    checked = isPrivacyAccepted,
                    onCheckedChange = { isPrivacyAccepted = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color.Cyan)
                )
                Text(
                    buildAnnotatedString {
                        append("Ich akzeptiere die ")
                        withStyle(style = SpanStyle(color = colorResource(id = R.color.blue), textDecoration = TextDecoration.Underline)) {
                            append("Datenschutzerklärung")
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.White
                )
            }


            Button(
                onClick = {
                    isEmailValid = isValidEmail(email.text)
                    if (isEmailValid){

                        navController.navigate("homeScreen")
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
                Text("Registrieren", color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Text(
                buildAnnotatedString {
                    append("Bereits registriert? ")
                    withStyle(style = SpanStyle(color = colorResource(id = R.color.blue), textDecoration = TextDecoration.Underline)) {
                        append("Einloggen")
                    }
                },
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("loginScreen")
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}
