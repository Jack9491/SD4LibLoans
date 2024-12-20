package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore

// Main composable function for the Search Screen
@Composable
fun SearchScreen(navController: NavHostController) {
    val firestore = FirebaseFirestore.getInstance() // Firestore instance for database access
    var books by remember { mutableStateOf(listOf<Map<String, Any>>()) } // State to hold book data
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // State for search text
    var genres by remember { mutableStateOf(listOf<String>()) } // List of genres available
    var selectedGenre by remember { mutableStateOf<String?>(null) } // Currently selected genre
    var expanded by remember { mutableStateOf(false) } // State for dropdown menu expansion

    // Fetch books and genres from Firestore on initial load
    LaunchedEffect(Unit) {
        firestore.collection("books").get()
            .addOnSuccessListener { result ->
                books = result.documents.map { doc -> doc.data ?: emptyMap() } // Map Firestore documents to data
                genres = books.mapNotNull { it["genre"]?.toString() }.distinct() // Extract unique genres
            }
    }

    // Main screen layout
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF101010)) // Set a consistent dark background
                .padding(16.dp) // Add padding around the content
        ) {
            // Row for the back button and screen title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() }, // Navigate back on button click
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back", // Back button description for accessibility
                        tint = Color(0xFFFBC02D) // Yellow tint for the icon
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Search Books",
                    color = Color(0xFFFBC02D), // Yellow text color
                    fontSize = 24.sp, // Large font size
                    fontWeight = FontWeight.Bold
                )
            }

            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it }, // Update search text on user input
                placeholder = { Text("Search...", color = Color.Gray) }, // Placeholder text
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search, // Search icon
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },
                singleLine = true, // Restrict to a single line
                shape = RoundedCornerShape(8.dp), // Rounded corners for the input field
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(56.dp) // Fixed height for the search bar
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown for genre selection
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expanded = true }, // Expand dropdown menu on button click
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(
                        text = selectedGenre ?: "Select Genre", // Display selected genre or placeholder
                        color = Color.Gray,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }, // Collapse dropdown on dismiss
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(text = genre) }, // Display genre option
                            onClick = {
                                selectedGenre = genre // Update selected genre
                                expanded = false // Collapse dropdown
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = "All Genres") }, // Option to reset genre filter
                        onClick = {
                            selectedGenre = null
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn for displaying filtered books
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between book rows
            ) {
                itemsIndexed(books.filter {
                    val title = it["title"]?.toString() ?: ""
                    val genre = it["genre"]?.toString()
                    title.contains(searchText.text, ignoreCase = true) && // Filter by search text
                            (selectedGenre == null || genre == selectedGenre) // Filter by selected genre
                }) { _, book ->
                    BookRow(
                        title = book["title"]?.toString() ?: "Unknown Title", // Book title
                        imageUrl = book["imageUrl"]?.toString() ?: "", // Book cover image URL
                        onDetailsClick = {
                            navController.navigate("bookDetailsScreen/${book["title"]}") // Navigate to book details
                        }
                    )
                }
            }
        }
    }
}

// Composable for displaying a single book row
@Composable
private fun BookRow(title: String, imageUrl: String, onDetailsClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFBC02D)), // Yellow background
        shape = RoundedCornerShape(8.dp), // Rounded corners for the card
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Book cover image
            AsyncImage(
                model = imageUrl, // Image URL from Firestore
                contentDescription = "Book Cover", // Accessibility description
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)), // Rounded corners for the image
                contentScale = ContentScale.Crop // Crop image to fit
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Book title and button
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    color = Color.White, // White text for title
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onDetailsClick, // Navigate to book details on button click
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "View Details", color = Color.White) // Button text
                }
            }
        }
    }
}
