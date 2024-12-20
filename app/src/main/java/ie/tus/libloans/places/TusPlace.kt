package ie.tus.libloans.places

import com.google.android.gms.maps.model.LatLng

data class TusPlace(
    val name: String,
    val description: String,
    val latitude: Double?,
    val longitude: Double?
) {
    fun position(): LatLng {
        return LatLng(latitude ?: 0.0, longitude ?: 0.0)
    }
}
