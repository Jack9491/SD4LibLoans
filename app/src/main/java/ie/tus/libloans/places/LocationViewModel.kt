package ie.tus.libloans.places

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.LocationServices

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val app = application
    var lastLocation = mutableStateOf<Location?>(null)
        private set

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    lastLocation.value = location
                } else {
                    Log.d("LibLoans", "Location is null")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("LibLoans", "Could not get current location: $exception")
            }
    }
}
