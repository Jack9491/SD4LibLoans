package ie.tus.libloans.navigation

sealed class Screen(val route: String) {

     object WelcomeScreen: Screen("welcomeScreen")
     object LoginScreen: Screen("loginScreen")
     object RegisterScreen: Screen("registerScreen")
     object HomeScreen: Screen("homeScreen")
     object SearchScreen: Screen("searchScreen")
     object LoanScreen: Screen("loanScreen")
     object BookDetailsScreen: Screen("bookDetailsScreen")
     object MapScreen: Screen("MapScreen")

}

val screens = listOf(
     Screen.WelcomeScreen,
     Screen.LoginScreen,
     Screen.RegisterScreen,
     Screen.HomeScreen,
     Screen.SearchScreen,
     Screen.LoanScreen,
     Screen.BookDetailsScreen,
     Screen.MapScreen
)