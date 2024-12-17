//package ie.tus.libloans.places
//
//import android.annotation.SuppressLint
//import android.app.Application
//import android.content.Context.LOCATION_SERVICE
//import android.location.Location
//import android.location.LocationManager
//import android.util.Log
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.AndroidViewModel
//import com.google.android.gms.location.LocationServices
//
//class LocationViewModel(application: Application) : AndroidViewModel(application) {
//    private val app = application
//    var lastLocation = mutableStateOf<Location?>(null)
//
//    @SuppressLint("MissingPermission")
//    fun getLastLocation() {
//        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)
//
//        if (!hasPermission()) return
//
//        fusedLocationProviderClient.lastLocation
//            .addOnSuccessListener { location ->
//                if (location != null) {
//                    lastLocation.value = location
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("LIBLOANS", "Could not get current location: $exception")
//            }
//    }
//
//    fun hasPermission(): Boolean {
//        val locationManager = app.getSystemService(LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//    }
//}
