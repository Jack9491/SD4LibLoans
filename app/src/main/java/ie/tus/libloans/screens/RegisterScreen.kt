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

class RegisterScreen(navController: NavHostController) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibLoansTheme {
                RegisterContent(onRegisterSubmit = { name, email, username, password ->
                    // Handle registration logic here
                })
            }
        }
    }
}

@Composable
fun RegisterContent(onRegisterSubmit: (String, String, String, String) -> Unit) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Register")
        TextField(value = name.value, onValueChange = { name.value = it }, label = { Text("Name") })
        TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        TextField(value = username.value, onValueChange = { username.value = it }, label = { Text("Username") })
        TextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") })
        Button(onClick = { onRegisterSubmit(name.value, email.value, username.value, password.value) }) {
            Text(text = "Register")
        }
    }
}

@Preview
@Composable
fun RegisterPreview() {
    LibLoansTheme {
        RegisterContent { _, _, _, _ -> }
    }
}
