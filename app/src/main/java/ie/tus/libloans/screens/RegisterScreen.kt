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

// RegisterScreen - Entry point for user registration with Firebase Authentication
@Composable
fun RegisterScreen(navController: NavHostController) {
    val context = LocalContext.current // Access current context for Toast messages
    val auth = remember { FirebaseAuth.getInstance() } // FirebaseAuth instance
    var isLoading by remember { mutableStateOf(false) } // Loading state for the register button

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Register screen content
        RegisterScreenContent(
            onRegisterSubmit = { email, password ->
                isLoading = true // Show loading state
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoading = false // Stop loading
                        if (task.isSuccessful) {
                            // Registration successful
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("loginScreen") // Navigate to LoginScreen
                        } else {
                            // Registration failed, display error
                            Toast.makeText(
                                context,
                                "Registration Failed: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },
            isLoading = isLoading // Pass loading state to content
        )
    }
}

// RegisterScreenContent - UI content for registration functionality
@Composable
private fun RegisterScreenContent(
    onRegisterSubmit: (String, String) -> Unit, // Callback for registration action
    isLoading: Boolean // Flag to show loading indicator
) {
    // State for input fields
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    // Main layout container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        // Screen Title
        Text(
            text = "Register",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name Input Field
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email Input Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Username Input Field
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
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

        // Register Button
        Button(
            onClick = {
                onRegisterSubmit(email.value, password.value) // Trigger registration action
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Disable button when loading
        ) {
            if (isLoading) {
                // Show loading spinner when registering
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Default button text
                Text(text = "Register")
            }
        }
    }
}
