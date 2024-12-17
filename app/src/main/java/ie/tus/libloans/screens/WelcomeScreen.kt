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

// WelcomeScreen - Entry point for the app's welcome page
@Composable
fun WelcomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize() // Fill the entire screen
    ) {
        // Welcome Screen Content with navigation actions
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

// WelcomeScreenContent - Displays the UI content for the welcome screen
@Composable
private fun WelcomeScreenContent(
    onLoginClick: () -> Unit,    // Callback for login button click
    onRegisterClick: () -> Unit  // Callback for register button click
) {
    // Column layout to arrange content vertically
    Column(
        modifier = Modifier
            .fillMaxSize() // Take up the full screen
            .padding(16.dp), // Add padding to the content
        verticalArrangement = Arrangement.Center, // Center content vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
    ) {
        // Title Text
        Text(
            text = "Welcome to LibLoans",
            fontSize = 24.sp, // Large font size
            fontWeight = FontWeight.Bold, // Bold font style
            color = Color.DarkGray // Text color
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space between title and buttons

        // Login Button
        Button(
            onClick = onLoginClick, // Trigger login callback
            modifier = Modifier
                .fillMaxWidth() // Make button fill the width
                .padding(horizontal = 32.dp) // Horizontal padding
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Space between buttons

        // Register Button
        Button(
            onClick = onRegisterClick, // Trigger register callback
            modifier = Modifier
                .fillMaxWidth() // Make button fill the width
                .padding(horizontal = 32.dp) // Horizontal padding
        ) {
            Text(text = "Register")
        }
    }
}

// Preview for the WelcomeScreenContent
@Preview
@Composable
fun WelcomeScreenPreview() {
    LibLoansTheme {
        WelcomeScreenContent(
            onLoginClick = {},    // Empty action for preview
            onRegisterClick = {}  // Empty action for preview
        )
    }
}
