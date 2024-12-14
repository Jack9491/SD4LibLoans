package ie.tus.libloans.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.tus.libloans.screens.LoginScreen
import ie.tus.libloans.screens.RegisterScreen
import ie.tus.libloans.screens.WelcomeScreen


@Composable
fun BuildNavigationGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route) {
        composable(Screen.WelcomeScreen.route) { WelcomeScreen(navController) }
        composable(Screen.RegisterScreen.route) { RegisterScreen(navController) }
        composable(Screen.LoginScreen.route) { LoginScreen(navController)  }
    }
}