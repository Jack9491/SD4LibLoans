package ie.tus.libloans.navigation


sealed class Screen(
    val route: String
){
    data object WelcomeScreen: Screen("welcomeScreen")
    data object LoginScreen: Screen("loginScreen")
    data object RegisterScreen: Screen("registerScreen")
}

val screens = listOf(
    Screen.WelcomeScreen,
    Screen.RegisterScreen,
    Screen.LoginScreen
)