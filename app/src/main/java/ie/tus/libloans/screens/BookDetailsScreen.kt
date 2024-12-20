package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(navController: NavHostController, bookName: String) {
    val firestore = FirebaseFirestore.getInstance()
    var book by remember { mutableStateOf<Map<String, Any>?>(null) }

    // Fetch the book details from Firestore
    LaunchedEffect(bookName) {
        firestore.collection("books").whereEqualTo("title", bookName).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    book = querySnapshot.documents[0].data
                }
            }
    }

    // Display the book details
    book?.let {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF101010) // Set a consistent dark background color
        ) {
            BookDetailsScreenContent(
                bookTitle = it["title"]?.toString() ?: "",
                bookDescription = it["description"]?.toString() ?: "",
                bookGenre = it["genre"]?.toString() ?: "",
                bookImageUrl = it["imageUrl"]?.toString() ?: "",
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
    bookImageUrl: String,
    onLoanClick: () -> Unit,
    onBackClick: () -> Unit
) {
    // Enable scrolling for the content
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Back button at the top
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFFBC02D)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Book Image
        AsyncImage(
            model = bookImageUrl,
            contentDescription = bookTitle,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp) // Set a maximum height for the image
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Book Title
        Text(
            text = bookTitle,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Genre Tag
        Text(
            text = "Genre: $bookGenre",
            color = Color(0xFFFBC02D),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .background(Color(0xFF1F1F1F), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Book Description
        Text(
            text = "Description",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = bookDescription,
            color = Color.Gray,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Loan Button
        Button(
            onClick = onLoanClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBC02D)),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = "Loan Book",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
