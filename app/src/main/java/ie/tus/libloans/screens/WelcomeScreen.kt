package ie.tus.libloans.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ie.tus.libloans.ui.theme.LibLoansTheme

@Composable
fun WelcomeScreen(navController: NavHostController) {

        Surface(
            modifier = Modifier
                .fillMaxSize()

        ) {
            WelcomeScreenContent(
                onLoginClick = {
                    // Navigate to the LoginScreen
                    navController.navigate("loginScreen")
                },
                onRegisterClick = {
                    // Navigate to the RegisterScreen
                    navController.navigate("registerScreen")
                }
            )
        }
    }


@Composable
private fun WelcomeScreenContent(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to LibLoans",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(text = "Register")
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    LibLoansTheme {
        WelcomeScreenContent(
            onLoginClick = {},
            onRegisterClick = {}
        )
    }
}
