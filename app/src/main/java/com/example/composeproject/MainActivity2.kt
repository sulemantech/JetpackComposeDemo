//package com.example.composeproject
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.composeproject.ui.theme.ComposeProjectTheme
//
//
//class MainActivity2 : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            ComposeProjectTheme {
//                LandlordInfoScreen()
//            }
//        }
//    }
//}
//
//@Composable
//fun LandlordInfoScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(rememberScrollState())
//    ) {
//        // Header Section
//        LogoAndTitle()
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Input Fields
//        LandlordInfoForm()
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Navigation Buttons
//        NavigationButtons()
//    }
//}
//
//@Composable
//fun LogoAndTitle() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Text(
//            text = "GIGA FIBER",
//            fontSize = 20.sp,
//            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "VERMIETERINFORMATIONEN",
//            fontSize = 18.sp,
//            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
//            color = Color(0xFF8A2BE2) // Purple Color
//        )
//        Text(
//            text = "Bitte trage jetzt die Informationen zu deinem Vermieter ein.",
//            fontSize = 14.sp,
//            color = Color.Gray
//        )
//    }
//}
//
//@Composable
//fun LandlordInfoForm() {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        CustomTextField("Firmenname")
//        CustomTextField("Ansprechpartner:in")
//
//        Row {
//            CustomTextField("PLZ", Modifier.weight(1f))
//            Spacer(modifier = Modifier.width(8.dp))
//            CustomTextField("Firmenstandort", Modifier.weight(1f))
//        }
//
//        Row {
//            CustomTextField("Straße", Modifier.weight(1f))
//            Spacer(modifier = Modifier.width(8.dp))
//            CustomTextField("Nr.", Modifier.weight(0.5f))
//        }
//
//        CustomTextField("E-Mail")
//        CustomTextField("Telefonnummer")
//    }
//}
//
//@Composable
//fun CustomTextField(label: String, modifier: Modifier = Modifier.fillMaxWidth()) {
//    var text by remember { mutableStateOf("") }
//
//    BasicTextField(
//        value = text,
//        onValueChange = { text = it },
//        textStyle = TextStyle(fontSize = 16.sp),
//        modifier = modifier
//            .padding(vertical = 4.dp)
//            .background(Color.LightGray, RoundedCornerShape(8.dp))
//            .padding(16.dp)
//            .height(50.dp)
//            .fillMaxWidth()
//    )
//}
//
//@Composable
//fun NavigationButtons() {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Button(
//            onClick = { /* Handle later action */ },
//            colors = ButtonDefaults.buttonColors(Color.LightGray)
//        ) {
//            Text("SPÄTER")
//        }
//
//        Button(
//            onClick = { /* Handle next action */ },
//            colors = ButtonDefaults.buttonColors(Color.Yellow)
//        ) {
//            Text("WEITER")
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewToDoApp() {
//    ComposeProjectTheme {
//        LandlordInfoScreen()
//    }
//}
