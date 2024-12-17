//package ie.tus.libloans.places
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class PlacesViewModel(application: Application) : AndroidViewModel(application) {
//
//    // Mock list of library places
//    private val _placesList = MutableLiveData<List<TusPlace>>(
//        listOf(
//            TusPlace("TUS Library Limerick", "Main Campus Library", 52.6638, -8.6267),
//            TusPlace("TUS Library Thurles", "Thurles Campus Library", 52.6816, -7.8025),
//            TusPlace("TUS Library Ennis", "Ennis Campus Library", 52.8479, -8.9824)
//        )
//    )
//
//    fun loadTusPlaces(): LiveData<List<TusPlace>> {
//        return _placesList
//    }
//}
