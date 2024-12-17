//package ie.tus.libloans.screens
//
//import android.location.Location
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.google.android.gms.maps.model.LatLng
//import ie.tus.libloans.places.LocationViewModel
//import ie.tus.libloans.places.PlacesViewModel
//import ie.tus.libloans.places.TusPlace
//import ie.tus.libloans.google.maps.android.compose.GoogleMap
//
//@Composable
//fun MapScreen(
//    locationViewModel: LocationViewModel = viewModel(),
//    placesViewModel: PlacesViewModel = viewModel()
//) {
//    val places by placesViewModel.loadTusPlaces().observeAsState(listOf())
//    val currentLocation by remember { locationViewModel.lastLocation }
//
//    LaunchedEffect(Unit) {
//        locationViewModel.getLastLocation()
//    }
//
//    PlacesMapScreen(
//        currentLocation = currentLocation,
//        placesList = places
//    )
//}
//
//@Composable
//private fun PlacesMapScreen(
//    currentLocation: Location?,
//    placesList: List<TusPlace>
//) {
//    val defaultLocation = LatLng(52.6638, -8.6267) // Limerick by default
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(
//            currentLocation?.let { LatLng(it.latitude, it.longitude) } ?: defaultLocation,
//            12f
//        )
//    }
//
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState,
//        uiSettings = MapUiSettings(zoomControlsEnabled = true),
//        properties = MapProperties(isMyLocationEnabled = true)
//    ) {
//        // Add markers for places
//        placesList.forEach { place ->
//            MarkerInfoWindowContent(
//                state = MarkerState(position = place.position()),
//                title = place.name,
//                snippet = place.description
//            )
//        }
//    }
//}
