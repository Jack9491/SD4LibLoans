package ie.tus.libloans.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import ie.tus.libloans.ui.theme.LibLoansTheme

class WelcomeScreen(navController: NavHostController) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LibLoansTheme {
                WelcomeContent(onLoginClick = {
                    startActivity(Intent(this, LoginScreen::class.java))
                }, onRegisterClick = {
                    startActivity(Intent(this, RegisterScreen::class.java))
                })
            }
        }
    }
}

@Composable
fun WelcomeContent(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome to LibLoans")
        Button(onClick = onLoginClick) {
            Text(text = "Login")
        }
        Button(onClick = onRegisterClick) {
            Text(text = "Register")
        }
    }
}

@Preview
@Composable
fun WelcomePreview() {
    LibLoansTheme {
        WelcomeContent({}, {})
    }
}