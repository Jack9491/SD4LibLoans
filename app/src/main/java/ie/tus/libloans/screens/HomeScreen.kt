package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import ie.tus.libloans.R
import ie.tus.libloans.navigation.Screen

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeScreenContent(
            onSearchClick = { navController.navigate(Screen.SearchScreen.route) },
            onLoansClick = { navController.navigate(Screen.LoanScreen.route) },
            onReturnsClick = { navController.navigate(Screen.ReturnsScreen.route) },
            onMapClick = { navController.navigate(Screen.MapScreen.route) },
            onLogoutClick = {
                FirebaseAuth.getInstance().signOut() // Log out the user
                navController.navigate(Screen.WelcomeScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true } // Clear backstack
                }
            }
        )
    }
}

@Composable
private fun HomeScreenContent(
    onSearchClick: () -> Unit,
    onLoansClick: () -> Unit,
    onReturnsClick: () -> Unit,
    onMapClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)), // Dark background
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .widthIn(max = 400.dp), // Limit the width for better UI on larger screens
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title at the top of the screen
            Text(
                text = "Welcome to LibLoans",
                color = Color(0xFFFBC02D),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )

            // Search Card
            HomeCard(
                title = "Search Library",
                description = "Browse and explore the library catalog",
                icon = R.drawable.ic_search,
                onClick = onSearchClick
            )

            // Loans Card
            HomeCard(
                title = "Your Loans",
                description = "View and manage your loaned books",
                icon = R.drawable.ic_loans,
                onClick = onLoansClick
            )

            // Returns Card
            HomeCard(
                title = "Book Returns",
                description = "Check and return your borrowed books",
                icon = R.drawable.ic_returns,
                onClick = onReturnsClick
            )

            // Map Card
            HomeCard(
                title = "Find a Library",
                description = "Locate the nearest TUS library on the map",
                icon = R.drawable.ic_map,
                onClick = onMapClick
            )

            // Logout Button
            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFFF57C00), Color(0xFFFBC02D)) // Gradient from orange to yellow
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Logout",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeCard(
    title: String,
    description: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() }
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon on the left
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )

            // Title and description text
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Navigation Arrow Icon
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Navigate",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
