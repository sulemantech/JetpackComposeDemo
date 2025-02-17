package com.example.composeproject

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.regex.Pattern

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isEmailValid by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // "G8WAY" Styled Text
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White, fontSize = 32.sp)) {
                        append("G8")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Cyan, fontSize = 32.sp)) {
                        append("WAY")
                    }
                },
                textAlign = TextAlign.Center
            )

            // Login Text
            Text(
                text = "Login",
                color = Color.Gray,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )

            // Email Input Field
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("E-Mail", color = Color.Gray, fontSize = 14.sp)
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        isEmailValid = isValidEmail(email.text)
                    },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon", tint = Color.Gray) },
                    placeholder = { Text("E-Mail", color = Color.Gray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isEmailValid) Color.Gray else Color.Red,
                        unfocusedBorderColor = if (isEmailValid) Color.Gray else Color.Red
                    ),
                    isError = !isEmailValid
                )

                // Show error message if email is invalid
                if (!isEmailValid) {
                    Text(
                        text = "Diese E-Mail ist nicht registriert.",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }

            // Login Button
            Button(
                onClick = { isEmailValid = isValidEmail(email.text) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008B8B)), // Dark cyan
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Login mit Magiclink", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Text(
                buildAnnotatedString {
                    append("Noch kein Konto? ")
                    withStyle(style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
                        append("Registrieren")
                    }
                },
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { /* Handle Register Click */ }
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

// ✅ Preview function to see UI in Android Studio
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
