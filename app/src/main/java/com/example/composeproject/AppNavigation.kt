package com.example.composeproject

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.composeproject.activities.AuthScreen
import com.example.composeproject.activities.EmailVerificationScreen
import com.example.composeproject.activities.G8WayScreen
import com.example.composeproject.activities.HomeScreen
import com.example.composeproject.activities.SearchRouteScreen
import com.example.composeproject.activities.SearchScreen
import com.example.composeproject.activities.UploadTicketScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background))
    ) {
        AnimatedNavHost(
            navController = navController,
            startDestination = "G8WayScreen",
            enterTransition = { slideInHorizontally(initialOffsetX = { 1100 }, animationSpec = tween(700)) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1100 }, animationSpec = tween(700)) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -1100 }, animationSpec = tween(700)) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { 1100 }, animationSpec = tween(700)) }
        ) {
            composable("G8WayScreen") { G8WayScreen(navController) }
            composable("EmailVerificationScreen") { EmailVerificationScreen(navController) }
            composable("homeScreen") { HomeScreen(navController) }
            composable("upload_ticket_screen") { UploadTicketScreen(navController) }
            composable("search_screen") { SearchScreen(navController) }
            composable("search_route_screen") { SearchRouteScreen(navController) }
            composable("auth_activity") { AuthScreen(navController) }
        }
    }
}
