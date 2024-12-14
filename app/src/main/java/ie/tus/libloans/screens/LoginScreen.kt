package ie.tus.libloans.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import ie.tus.libloans.ui.theme.LibLoansTheme

class LoginScreen(navController: NavHostController) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibLoansTheme {
                LoginContent(onLoginSubmit = { username, password ->
                    // Handle login logic here
                })
            }
        }
    }
}

@Composable
fun LoginContent(onLoginSubmit: (String, String) -> Unit) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Login")
        TextField(value = username.value, onValueChange = { username.value = it }, label = { Text("Username") })
        TextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") })
        Button(onClick = { onLoginSubmit(username.value, password.value) }) {
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    LibLoansTheme {
        LoginContent { _, _ -> }
    }
}