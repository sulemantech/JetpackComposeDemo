package com.example.composeproject.activities

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composeproject.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun AuthScreen(navController: NavController) {
    var isLogin by remember { mutableStateOf(true) }
    var fullName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    val robotoFontFamily = FontFamily(Font(R.font.roboto_light))
    val scrollState = rememberScrollState()
    var isPrivacyAccepted by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    var isTermsAccepted by remember { mutableStateOf(false) }
    val backgroundColor = colorResource(id = R.color.background)
    val checkboxFocusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(16.dp)
            .imePadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .windowInsetsPadding(WindowInsets.ime)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = stringResource(id = R.string.image_from_resources),
                modifier = Modifier.size(147.dp, 39.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(42.dp))

            Text(
                text = if (isLogin) stringResource(id = R.string.login) else stringResource(id = R.string.register),
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (!isLogin) {
                AuthTextField(
                    label = stringResource(id = R.string.name),
                    value = fullName,
                    onValueChange = { fullName = it },
                    leadingIconRes = R.drawable.ic_profile

                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            AuthTextField(
                label = stringResource(id = R.string.e_mail),
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = true
                },
                leadingIconRes = R.drawable.ic_message,
                isError = !isEmailValid
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                if (!isEmailValid) {
                    Text(
                        text = if (isLogin) stringResource(id = R.string.diese_e_mail_ist_nicht_registriert) else stringResource(
                            id = R.string.diese_e_mail_ist_bereits_registriert
                        ),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, top = 4.dp)
                    )
                }
            }
            if (isLogin) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isLogin) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    CustomCheckbox1(
                        checked = isTermsAccepted,
                        onCheckedChange = { isTermsAccepted = it },
                        modifier = Modifier
                            .padding(start = 2.dp, top = 2.dp)
                            .focusRequester(checkboxFocusRequester)
                    )

                    Text(
                        buildAnnotatedString {
                            append(stringResource(id = R.string.Ich_akzeptiere_die))
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    color = colorResource(id = R.color.blue),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(
                                    stringResource(
                                        id = R.string.Allgemeinen,
                                        ""
                                    )
                                )
                            }
                        },
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.btn_text_field),
                        maxLines = 2,
                        softWrap = true,
                        lineHeight = 20.sp,
                        modifier = Modifier
                            .padding(start = 7.5.dp)
                            .clickable {
                                val url =
                                    "https://g8way-app.com/impressum.html"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    CustomCheckbox(
                        checked = isPrivacyAccepted,
                        onCheckedChange = { isPrivacyAccepted = it },
                        modifier = Modifier.padding(start = 2.dp)
                    )
                    Text(
                        buildAnnotatedString {
                            append(stringResource(id = R.string.Ich_akzeptiere_die))
                            append(" ")

                            withStyle(
                                style = SpanStyle(
                                    color = colorResource(id = R.color.blue),
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(stringResource(id = R.string.Datenschutzerklärung))
                            }
                        },
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.btn_text_field),
                        maxLines = 2,
                        softWrap = true,
                        modifier = Modifier
                            .padding(start = 7.5.dp)
                            .clickable {
                                val url =
                                    "https://g8way-app.com/datenschutz.html"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            Button(
                onClick = {
                    isEmailValid = isValidEmail1(email.text)
                    if (isEmailValid) {
                        if (isLogin) {
                            navController.navigate("EmailVerificationScreen")
                        } else {
                            navController.navigate("HomeScreen")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isEmailValid || email.text.isEmpty())
                        colorResource(id = R.color.teal_700)
                    else
                        colorResource(id = R.color.blue)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (isLogin) stringResource(id = R.string.login_mit_magiclink) else stringResource(
                        id = R.string.register
                    ),
                    color = colorResource(id = R.color.text_black),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                buildAnnotatedString {
                    append(
                        if (isLogin) stringResource(id = R.string.no_account) + " "
                        else stringResource(id = R.string.bereits_registriert) + " "
                    )
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.blue),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(
                            if (isLogin) stringResource(id = R.string.register)
                            else stringResource(id = R.string.Einloggen)
                        )
                    }
                },
                fontSize = 14.sp,
                color = colorResource(id = R.color.btn_text_field),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        isLogin = !isLogin
                        coroutineScope.launch {
                            scrollState.scrollTo(0)
                        }
                    }

            )
        }
    }
}

@Composable
fun AuthTextField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isError: Boolean = false,
    leadingIconRes: Int? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = colorResource(id = R.color.btn_text_field),
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            leadingIcon = {
                leadingIconRes?.let {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 14.dp)
                    )
                }
            },
            placeholder = {
                Text(
                    text = label,
                    color = colorResource(id = R.color.text_field_hint),
                    fontWeight = FontWeight.Medium
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.blue),
                unfocusedBorderColor = if (isError) Color.Red else colorResource(id = R.color.text_border),
                cursorColor = colorResource(id = R.color.blue)
            ),
            isError = isError,
            textStyle = TextStyle(color = Color.White)
        )
    }
}

@Composable
fun ErrorText(message: String) {
    Text(
        text = message,
        color = Color.Red,
        fontSize = 14.sp,
        modifier = Modifier.padding(start = 6.dp, top = 4.dp)
    )
}

fun isValidEmail1(email: String): Boolean {
    val emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    return emailPattern.matcher(email).matches()
}

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(22.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(
                1.dp,
                if (checked) colorResource(id = R.color.blue) else colorResource(id = R.color.text_border),
                RoundedCornerShape(6.dp)
            )
            .background(if (checked) colorResource(id = R.color.background) else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Transparent,
                uncheckedColor = Color.Transparent,
                checkmarkColor = Color.White
            ),
            modifier = Modifier
                .size(22.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthScreen() {
    AuthScreen(navController = rememberNavController())
}

