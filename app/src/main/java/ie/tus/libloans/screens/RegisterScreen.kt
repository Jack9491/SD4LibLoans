package ie.tus.libloans.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
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
@OptIn(ExperimentalMaterial3Api::class)
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
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        // Screen Title
        Text(
            text = "Register",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFBC02D),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Name Input Field
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFBC02D),
                unfocusedBorderColor = Color.Gray,
                //textColor = Color.White,
                cursorColor = Color.White
            )
        )

        // Email Input Field
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFBC02D),
                unfocusedBorderColor = Color.Gray,
                //textColor = Color.White,
                cursorColor = Color.White
            )
        )

        // Username Input Field
        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFBC02D),
                unfocusedBorderColor = Color.Gray,
                //textColor = Color.White,
                cursorColor = Color.White
            )
        )

        // Password Input Field
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password", color = Color.Gray) },
            textStyle = TextStyle(color = Color.White),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFBC02D),
                unfocusedBorderColor = Color.Gray,
                cursorColor = Color.White
            )
        )

        // Register Button
        Button(
            onClick = {
                onRegisterSubmit(email.value, password.value) // Trigger registration action
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFBC02D),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (isLoading) {
                // Show loading spinner when registering
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Default button text
                Text(
                    text = "Register",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
