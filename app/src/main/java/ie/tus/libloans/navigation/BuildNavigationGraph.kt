package ie.tus.libloans.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import ie.tus.libloans.screens.BookDetailsScreen
import ie.tus.libloans.screens.BorrowScreen
import ie.tus.libloans.screens.HomeScreen
import ie.tus.libloans.screens.LoanScreen
import ie.tus.libloans.screens.LoginScreen
import ie.tus.libloans.screens.MapScreen
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
        composable(Screen.LoginScreen.route) { LoginScreen(navController) }
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.SearchScreen.route) { SearchScreen(navController) }
        composable("loanScreen") { LoanScreen(navController) }
        composable("returnsScreen") { ReturnsScreen(navController) }
        composable("mapScreen") { MapScreen(navController) }


        // BookDetailsScreen with bookName parameter
        composable(
            route = "bookDetailsScreen/{bookName}",
            arguments = listOf(navArgument("bookName") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookName = backStackEntry.arguments?.getString("bookName")
            bookName?.let {
                BookDetailsScreen(navController = navController, bookName = it)
            }
        }

        // BorrowScreen with bookName and userId parameters
        composable(
            route = "borrowScreen/{bookName}",
            arguments = listOf(navArgument("bookName") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookName = backStackEntry.arguments?.getString("bookName")
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            if (userId != null && bookName != null) {
                BorrowScreen(navController = navController, bookName = bookName, userId = userId)
            } else {
                // Handle error: user not logged in or bookName missing
                navController.navigate("loginScreen")
            }
        }
    }
}
