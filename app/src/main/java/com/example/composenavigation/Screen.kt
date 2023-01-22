package com.example.composenavigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object MainScreen : Screen("main_screen")
    object DetailsScreen : Screen("details_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
