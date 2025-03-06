package com.example.composeproject.activities

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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.composeproject.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController



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
      //  composable("loginScreen") { LoginScreen(navController) }
      //  composable("registerScreen") { RegisterScreen(navController) }
        composable("EmailVerificationScreen") { EmailVerificationScreen(navController) }
        composable("homeScreen") { HomeScreen(navController) }
        composable("upload_ticket_screen") { UploadTicketScreen(navController) }
        composable("search_screen") { SearchScreen(navController) }
        composable("search_route_screen") { SearchRouteScreen(navController) }
        composable("auth_activity") { AuthScreen(navController) }
    }
}


@Composable
fun G8WayScreen(navController: NavController) {
    val systemUiController = rememberSystemUiController()
    val backgroundColor = colorResource(id = R.color.background)

    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.aircraftstartpage))

    val robotoFontFamily = FontFamily(
        Font(R.font.roboto_light)
    )
    val robotoFontFamily1 = FontFamily(
        Font(R.font.roboto_regular)
    )
    val robotoFontFamily2 = FontFamily(
        Font(R.font.roboto_regular, weight = FontWeight.SemiBold)
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
           // verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .size(250.dp, 120.dp)
                    .graphicsLayer(rotationZ = -8f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_g8_way_welcome),
                contentDescription = stringResource(id = R.string.image_from_resources),
                modifier = Modifier.size(147.dp, 39.dp),
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))

            Text(
                text = stringResource(id = R.string.register_and_navigate),
                color = colorResource(id = R.color.text_field),
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = robotoFontFamily1,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(colorResource(id = R.color.btnColor), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                ) {
                    Text(
                        text = stringResource(id = R.string.nearest_airport),
                        color = colorResource(id = R.color.btn_text_field),
                        fontSize = 16.sp,
                        fontFamily = robotoFontFamily,
                        fontWeight = FontWeight.W300,
                        lineHeight = 24.sp
                    )
                    Text(
                        text = stringResource(id = R.string.munich_airport),
                        color = colorResource(id = R.color.btn_text_field),
                        fontSize = 16.sp,
                        fontFamily = robotoFontFamily1,
                        fontWeight = FontWeight.W400,
                    )

                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    navController.navigate("auth_activity")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp)
                    .height(64.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Start",
                    color = colorResource(id = R.color.text_black),
                    fontSize = 16.sp,
                    fontFamily = robotoFontFamily1,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewG8WayScreen() {
    G8WayScreen(navController = rememberNavController())
}
