package com.example.composeproject.activities

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
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
    var showTermsDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

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
                CustomCheckbox1(
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
                    color = colorResource(id = R.color.text_black),
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
                color = colorResource(id = R.color.btn_text_field),
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    navController.navigate("loginScreen")
                }
            )
//            if (showTermsDialog) {
//                TermsAndConditionsDialog(onDismiss = { showTermsDialog = false })
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditionsDialog(onDismiss: () -> Unit) {
    val robotoFontFamily1 = FontFamily(Font(R.font.roboto_regular))

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = colorResource(id = R.color.btnColor)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Allgemeine\nGeschäftsbedingungen",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.btn_text_field),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 20.dp)
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.TopEnd)
                        .padding(bottom = 20.dp, end = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_close),
                        contentDescription = "Close",
                        tint = colorResource(id = R.color.btn_text_field)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Willkommen bei GBWAY!\n" +
                        "Bevor Sie unsere App nutzen, nehmen Sie sich bitte einen Moment Zeit, um unsere Allgemeinen Geschäftsbedingungen zu lesen. " +
                        "Durch den Zugriff auf oder die Nutzung unserer Dienste erklären Sie sich mit diesen Bedingungen einverstanden. Bitte lesen Sie sie sorgfältig durch.",
                color = colorResource(id = R.color.btn_text_field),
                fontSize = 16.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column {
                listOf(
                    "1. Zustimmung zu den Bedingungen:\n" +
                            "Durch die Nutzung unserer App erklären Sie sich damit einverstanden, die Bedingungen dieser Vereinbarung einzuhalten und rechtlich daran gebunden zu sein.",

                    "2. Änderungen der Bedingungen:\n" +
                            "Wir behalten uns das Recht vor, diese Bedingungen jederzeit zu ändern. Sie verpflichten sich, diese regelmäßig zu überprüfen, um über etwaige Änderungen informiert zu sein.",

                    "3. Pflichten des Nutzers:\n" +
                            "Sie verpflichten sich, die App in Übereinstimmung mit allen geltenden Gesetzen und Vorschriften zu nutzen.",

                    "4. Haftungsbeschränkung:\n" +
                            "Wir haften nicht für indirekte, beiläufige oder Folgeschäden, die aus der Nutzung der App entstehen."
                ).forEach { term ->
                    Text(
                        text = term,
                        color = colorResource(id = R.color.btn_text_field),
                        fontSize = 16.sp,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Akzeptieren",
                    color = colorResource(id = R.color.text_black),
                    fontSize = 16.sp,
                    fontFamily = robotoFontFamily1,
                    fontWeight = FontWeight.W600
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 40.dp))
        }
    }
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
            .background(if (checked)  colorResource(id = R.color.background)  else Color.Transparent),
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
