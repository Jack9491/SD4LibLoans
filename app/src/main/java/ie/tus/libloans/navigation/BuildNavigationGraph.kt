package ie.tus.libloans.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ie.tus.libloans.screens.BookDetailsScreen
import ie.tus.libloans.screens.HomeScreen
import ie.tus.libloans.screens.LoanScreen
import ie.tus.libloans.screens.LoginScreen
import ie.tus.libloans.screens.RegisterScreen
import ie.tus.libloans.screens.ReturnsScreen
import ie.tus.libloans.screens.SearchScreen
import ie.tus.libloans.screens.WelcomeScreen
import ie.tus.libloans.viewmodel.HomeViewModel


@Composable
fun BuildNavigationGraph(
    homeViewModel: HomeViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.WelcomeScreen.route) {
        composable(Screen.WelcomeScreen.route) { WelcomeScreen(navController) }
        composable(Screen.RegisterScreen.route) { RegisterScreen(navController) }
        composable(Screen.LoginScreen.route) { LoginScreen(navController)  }
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.SearchScreen.route) { SearchScreen(navController) }
        composable("loanScreen") { LoanScreen(navController) }
        composable("returnsScreen") { ReturnsScreen(navController) }
        composable(Screen.BookDetailsScreen.route) { BookDetailsScreen(navController) }
        //composable(Screen.MapScreen.route) { MapScreen(navController) }
    }
}