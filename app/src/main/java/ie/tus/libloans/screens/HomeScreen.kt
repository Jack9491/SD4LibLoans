package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ie.tus.libloans.R
import ie.tus.libloans.navigation.Screen

// Main Home Screen - Entry point for navigation options
@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeScreenContent(
            onSearchClick = { navController.navigate(Screen.SearchScreen.route) }, // Navigate to Search
            onLoansClick = { navController.navigate(Screen.LoanScreen.route) },   // Navigate to Loans
            onReturnsClick = { navController.navigate(Screen.ReturnsScreen.route) }, // Navigate to Returns
            onMapClick = { navController.navigate(Screen.MapScreen.route) }      // Navigate to Map
        )
    }
}

// Home Screen Content - Displays Cards for different options
@Composable
private fun HomeScreenContent(
    onSearchClick: () -> Unit,
    onLoansClick: () -> Unit,
    onReturnsClick: () -> Unit,
    onMapClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)) // Dark background
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Home Title
        Text(
            text = "Home",
            color = Color(0xFFFBC02D), // Yellow text color
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Card
        HomeCard(
            title = "Search",
            description = "Click here to view the Library Directory",
            icon = R.drawable.ic_search,
            onClick = onSearchClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Loans Card
        HomeCard(
            title = "Loans",
            description = "Click here to access your current loans",
            icon = R.drawable.ic_loans,
            onClick = onLoansClick
        )

        // Returns Card
        HomeCard(
            title = "Returns",
            description = "Click here to access your current returns",
            icon = R.drawable.ic_loans,
            onClick = onReturnsClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Map Card
        HomeCard(
            title = "Find Nearest Library",
            description = "Click here to view the nearest TUS library on the map",
            icon = R.drawable.ic_map,
            onClick = onMapClick
        )
    }
}

// Reusable Card Component for Home Options
@Composable
private fun HomeCard(
    title: String,         // Card Title
    description: String,   // Description under the title
    icon: Int,             // Resource ID for the card icon
    onClick: () -> Unit    // Click callback
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Trigger click callback
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)), // Yellow background
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Icon
            Icon(
                painter = painterResource(id = icon),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Title and Description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    color = Color.White.copy(alpha = 0.7f), // Slightly transparent text
                    fontSize = 14.sp
                )
            }

            // Right Arrow Icon
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Navigate",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
