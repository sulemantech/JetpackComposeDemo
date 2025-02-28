package com.example.composeproject.activities

import android.util.Patterns
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var isPrivacyAccepted by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var isNameFocused by remember { mutableStateOf(false) }
    val nameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val checkboxFocusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current



    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_light)
    )
    val robotoFontFamily1 = FontFamily(
        Font(R.font.roboto_regular)
    )
    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

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
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = robotoFontFamily,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(40.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.name),
                    color = Color.White,
                    fontSize = 14.sp,
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
                            color = Color.Gray
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
                        unfocusedBorderColor = if (isNameFocused) colorResource(id = R.color.blue) else Color.Gray,
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
                    color = Color.White,
                    fontSize = 14.sp,
//                    fontFamily = robotoFontFamily,
//                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(bottom = 8.dp, top = 10.dp)
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
                            contentDescription = stringResource(id = R.string.e_mail_Icon),
                            tint = Color.White,
                            modifier = Modifier.padding(start = 14.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.e_mail),
                            color = Color.Gray
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
                            isEmailValid -> Color.Gray
                            else -> Color.Red
                        },
                        cursorColor = colorResource(id = R.color.blue)
                    ),
                    isError = !isEmailValid,
                    textStyle = TextStyle(color = Color.White),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.clearFocus()
                            checkboxFocusRequester.requestFocus()                        }
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
                CustomCheckbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it },
                    modifier = Modifier
                        .padding(start = 5.dp, top = 2.dp)
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
                    color = Color.White,
                    maxLines = 2,
                    softWrap = true,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(start = 7.5.dp)
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
                    modifier = Modifier.padding(start = 5.dp)
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
                                stringResource(id = R.string.Datenschutzerklärung)
                            )
                        }
                    },
                    fontSize = 14.sp,
                    color = Color.White,
                    maxLines = 2,
                    softWrap = true,
                    modifier = Modifier.padding(start = 7.5.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    isEmailValid = isValidEmail(email.text)
                    if (isEmailValid) {

                        navController.navigate("homeScreen")
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isEmailValid || email.text.isEmpty())
                        colorResource(id = R.color.teal_700)
                    else
                        colorResource(id = R.color.blue)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
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
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("loginScreen")
                }
            )
        }
    }
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
                if (checked) colorResource(id = R.color.blue) else Color.Gray,
                RoundedCornerShape(6.dp)
            )
            .background(if (checked) Color.Black else Color.Transparent),
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
fun PreviewRegisterScreen() {
    RegisterScreen(navController = rememberNavController())
}
