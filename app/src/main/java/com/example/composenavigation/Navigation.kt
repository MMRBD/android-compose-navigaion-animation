package com.example.composenavigation

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailsScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "MokleSur Rahman"
                    nullable = true
                }
            )
        ) { entry ->
            DetailsScreen(name = entry.arguments?.getString("name"))
        }
    }
}

@Composable
fun SplashScreen(
    navController: NavController
) {

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.6f,
            animationSpec = tween(
                delayMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )

        delay(3000L)
        navController.navigate(Screen.MainScreen.route)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.programmer_re_owql),
            contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )

    }

}

@Composable
fun MainScreen(
    navController: NavController
) {

    var test by remember {
        mutableStateOf(value = "")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        TextField(
            value = test,
            onValueChange = { test = it },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(18.dp))

        Button(
            onClick = {
                navController.navigate(Screen.DetailsScreen.withArgs(test))
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Go To Details")
        }

    }

}

@Composable
fun DetailsScreen(
    name: String?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {
        Text(text = "Hello! $name", style = MaterialTheme.typography.h4)
    }

}