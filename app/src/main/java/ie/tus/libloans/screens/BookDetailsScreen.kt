// Updated BookDetailsScreen
package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(navController: NavHostController, bookName: String) {
    val firestore = FirebaseFirestore.getInstance()
    var book by remember { mutableStateOf<Map<String, Any>?>(null) }

    LaunchedEffect(bookName) {
        firestore.collection("books").whereEqualTo("title", bookName).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    book = querySnapshot.documents[0].data
                }
            }
    }

    book?.let {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            BookDetailsScreenContent(
                bookTitle = it["title"]?.toString() ?: "",
                bookDescription = it["description"]?.toString() ?: "",
                bookGenre = it["genre"]?.toString() ?: "",
                onLoanClick = {
                    navController.navigate("borrowScreen/${it["title"]}")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
private fun BookDetailsScreenContent(
    bookTitle: String,
    bookDescription: String,
    bookGenre: String,
    onLoanClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFBC02D)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = bookTitle,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Description: $bookDescription",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Genre: $bookGenre",
            color = Color.Gray,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onLoanClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D))
        ) {
            Text(text = "Loan Book", color = Color.White)
        }
    }
}