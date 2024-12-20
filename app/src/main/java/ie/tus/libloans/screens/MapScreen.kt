package ie.tus.libloans.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    // Predefined library locations
    val libraryLocations = listOf(
        LatLng(52.676796, -8.648952), // Limerick
        LatLng(52.6816, -7.8025), // Thurles
        LatLng(52.8479, -8.9824)  // Ennis
    )

    // Limerick as the initial map center
    val centerLocation = LatLng(52.6638, -8.6267)

    // Camera position state to center on Limerick
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(centerLocation, 12f)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF101010))) {
        Column {
            // Top Bar
            TopAppBar(
                title = {
                    Text(
                        text = "Find Your Library",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFFBC02D))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Google Map
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.White)
            ) {
                GoogleMap(
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = false), // Disable MyLocation
                    uiSettings = MapUiSettings(zoomControlsEnabled = true, scrollGesturesEnabled = true), // Enable gestures
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Add a marker for each library
                    libraryLocations.forEach { location ->
                        Marker(
                            state = MarkerState(position = location),
                            title = "Library Location",
                            snippet = "Explore this library"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Informational Footer
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFBC02D))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Zoom and move the map to locate your nearest library.",
                    color = Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
