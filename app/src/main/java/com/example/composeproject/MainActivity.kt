package com.example.composeproject

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
             AppNavigation(navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "G8WayScreen") {
        composable("G8WayScreen") { G8WayScreen(navController) }
        composable("loginScreen") { LoginScreen(navController) }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("EmailVerificationScreen") { EmailVerificationScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("upload_ticket_screen") { UploadTicketScreen(navController) }
    }
}


@Composable
fun G8WayScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.aircraftstartpage))

    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_regular) 
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(210.dp, 100.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = "Image from resources",
                modifier = Modifier.size(147.dp,39.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "Registrieren und mühelos navigieren",
                color = colorResource(id = R.color.text_field),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = robotoFontFamily,
                modifier = Modifier.padding(top = 16.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color.DarkGray, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Nächster Flughafen:", color = colorResource(id = R.color.btn_text_field), fontSize = 16.sp)
                    Text("Flughafen München (334 km)", color = colorResource(id = R.color.btn_text_field), fontSize = 16.sp)
                }
            }



            Button(
                onClick = {
                    navController.navigate("loginScreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Start", color = Color.Black, fontSize = 18.sp)
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewG8WayScreen() {
    G8WayScreen(navController = rememberNavController())
}
