package com.example.composeproject

import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState


@Composable
fun EmailVerificationScreen (navController: NavController){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.checkmails))
    val progress by animateLottieCompositionAsState(composition)

    val customFont = FontFamily(
        Font(R.font.roboto_light, FontWeight.Light)
    )


    Box(
    modifier = Modifier
    .fillMaxSize()
    .background(color = colorResource(id = R.color.background))
    .padding(16.dp),
    contentAlignment = Alignment.Center
    )

    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            LottieAnimation(composition, progress, modifier = Modifier.size(150.dp))

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_g8way),
                contentDescription = "Image from resources",
                modifier = Modifier.size(147.dp,39.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(0.dp))

            Text(
                text = "Bitte überprüfe Deine \n" +
                        "E-Mails und tippe auf \n" +
                        "den Link.",
                color = colorResource(id = R.color.email_text_field),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontFamily = customFont,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 36.dp)
            )


        }

        }
    }

@Preview(showBackground = true)
@Composable
fun PreviewEmailVerification(){
    EmailVerificationScreen(navController =rememberNavController())
}