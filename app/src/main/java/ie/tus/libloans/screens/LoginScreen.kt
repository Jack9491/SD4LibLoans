package ie.tus.libloans.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

// LoginScreen - Entry point for user login with Firebase Authentication
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() } // FirebaseAuth instance
    var isLoading by remember { mutableStateOf(false) } // Loading state for login button

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Content of the Login Screen
        LoginScreenContent(
            onLoginSubmit = { email, password ->
                isLoading = true // Show loading state
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoading = false // Stop loading
                        if (task.isSuccessful) {
                            // Login successful
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("homeScreen") // Navigate to home
                        } else {
                            // Login failed, show error message
                            Toast.makeText(
                                context,
                                "Login Failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },
            isLoading = isLoading
        )
    }
}

// LoginScreenContent - UI content for login functionality
@Composable
private fun LoginScreenContent(
    onLoginSubmit: (String, String) -> Unit, // Callback for login action
    isLoading: Boolean // Flag to show loading indicator
) {
    // State for email and password fields
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Main layout container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen Title
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email Input Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input Field
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(), // Hide password text
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = { onLoginSubmit(email.value, password.value) }, // Trigger login action
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Disable button while loading
        ) {
            if (isLoading) {
                // Show loading spinner when login is in progress
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Default button text
                Text(text = "Login")
            }
        }
    }
}
