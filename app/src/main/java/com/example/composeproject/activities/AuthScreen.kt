package com.example.composeproject.activities

import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composeproject.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@Composable
fun AuthScreen(navController: NavController) {
    var isLogin by remember { mutableStateOf(true) }

    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Crossfade(
            targetState = isLogin,
            animationSpec = tween(durationMillis = 1000),
            label = "Auth Animation"
        ) { loginState ->
            if (loginState) {
                LoginScreen1(navController) { isLogin = false }
            } else {
                RegisterScreen1(navController) { isLogin = true }
            }
        }
    }
}

@Composable
fun LoginScreen1(navController: NavController, onSwitch: () -> Unit) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    val robotoFontFamily = FontFamily(Font(R.font.roboto_light))
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val robotoFontFamily1 = FontFamily(
        Font(R.font.roboto_regular)
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
            // verticalArrangement = Arrangement.spacedBy(16.dp),
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
                text = stringResource(id = R.string.login),
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                // modifier = Modifier.padding(bottom = 40.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.e_mail),
                    color = colorResource(id = R.color.btn_text_field),
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
                            color = colorResource(id = R.color.text_field_hint),
                            fontWeight = FontWeight.Medium

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
                            isEmailValid -> colorResource(id = R.color.text_border)
                            else -> Color.Red
                        },
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    isError = !isEmailValid,
                    textStyle = TextStyle(color = Color.White)
                )

                if (!isEmailValid) {
                    Text(
                        text = stringResource(id = R.string.diese_e_mail_ist_nicht_registriert),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))


                Button(
                    onClick = {
                        coroutineScope.launch {
                            isEmailValid = isValidEmail1(email.text)
                            if (isEmailValid) {
                                delay(500)
                                navController.navigate("EmailVerificationScreen")
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
                        text = if (!isEmailValid) "Einloggen" else stringResource(id = R.string.login_mit_magiclink),
                        color = colorResource(id = R.color.text_black),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500
                    )
                }

            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                buildAnnotatedString {
                    append(stringResource(id = R.string.no_account))
                    append(" ")
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
                color = colorResource(id = R.color.btn_text_field),
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onSwitch() }
                //  navController.navigate("registerScreen")
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
fun RegisterScreen1(navController: NavController, onSwitch: () -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var isPrivacyAccepted by remember { mutableStateOf(false) }
    var isNameFocused by remember { mutableStateOf(false) }
    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val checkboxFocusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showTermsError by remember { mutableStateOf(false) }
    var showPrivacyError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_light)
    )
    val robotoFontFamily1 = FontFamily(
        Font(R.font.roboto_regular)
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
            //  verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
            // .windowInsetsPadding(WindowInsets.ime)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = stringResource(id = R.string.image_from_resources),
                modifier = Modifier.size(147.dp, 39.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(33.dp))

            Text(
                text = stringResource(id = R.string.register),
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.name),
                    color = colorResource(id = R.color.btn_text_field),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = stringResource(id = R.string.Profile_Icon),
                            tint = Color.White,
                            modifier = Modifier.padding(start = 15.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.name),
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium

                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(nameFocusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                isNameFocused = true
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.blue),
                        unfocusedBorderColor = if (isNameFocused) colorResource(id = R.color.blue) else colorResource(
                            id = R.color.text_border
                        ),
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    textStyle = TextStyle(color = Color.White),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Words

                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            emailFocusRequester.requestFocus()
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.e_mail),
                    color = colorResource(id = R.color.btn_text_field),
                    fontSize = 14.sp,
//                    fontFamily = robotoFontFamily,
//                    fontWeight = FontWeight.W400,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp, top = 10.dp)
                )

                fun isValidEmail1(email: String): Boolean {
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
                            contentDescription = stringResource(id = R.string.e_mail_Icon),
                            tint = Color.White,
                            modifier = Modifier.padding(start = 14.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.e_mail),
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium

                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.blue),
                        unfocusedBorderColor = when {
                            isFocused -> colorResource(id = R.color.blue)
                            isEmailValid -> colorResource(id = R.color.text_border)
                            else -> Color.Red
                        },
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    isError = !isEmailValid,
                    textStyle = TextStyle(color = Color.White),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                            checkboxFocusRequester.requestFocus()
                        }
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )

                if (!isEmailValid) {
                    Text(
                        text = stringResource(id = R.string.diese_e_mail_ist_bereits_registriert),
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                CustomCheckbox1(
                    checked = isTermsAccepted,
                    onCheckedChange = {
                        isTermsAccepted = it
                        if (it) showTermsError = false
                    },
                    modifier = Modifier
                        .padding(start = 2.dp, top = 2.dp)
                        .focusRequester(checkboxFocusRequester)
                        .border(
                            1.dp,
                            if (showTermsError) Color.Red else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )

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
                    lineHeight = 24.sp,
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
                CustomCheckbox1(
                    checked = isPrivacyAccepted,
                    onCheckedChange = {
                        isPrivacyAccepted = it
                        if (it) showPrivacyError = false
                    },
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .border(
                            1.dp,
                            if (showTermsError) Color.Red else Color.Transparent,
                            RoundedCornerShape(4.dp)
                        )

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

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    isEmailValid = isValidEmail1(email.text)
                    if (!isTermsAccepted) showTermsError = true
                    if (!isPrivacyAccepted) showPrivacyError = true
                    if (isEmailValid && email.text.isNotEmpty() && isTermsAccepted && isPrivacyAccepted) {
                        navController.navigate("homeScreen")
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isEmailValid || email.text.isEmpty() || !isTermsAccepted || !isPrivacyAccepted)
                        colorResource(id = R.color.teal_700)
                    else
                        colorResource(id = R.color.blue)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = colorResource(id = R.color.text_black),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                buildAnnotatedString {
                    append(stringResource(id = R.string.bereits_registriert))
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.blue),
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(
                            stringResource(id = R.string.Einloggen)
                        )
                    }
                },
                fontSize = 14.sp,
                color = colorResource(id = R.color.btn_text_field),
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onSwitch() }
                //  navController.navigate("registerScreen")
            )
//            if (showTermsDialog) {
//                TermsAndConditionsDialog(onDismiss = { showTermsDialog = false })
//            }
        }
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
fun CustomCheckbox1(
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

