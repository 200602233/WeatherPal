package com.example.weatherpal.repository

import android.view.View
import android.widget.Toast
import com.example.weatherpal.model.SavedCityModel
import com.example.weatherpal.model.WeatherModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.Callback
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

// convert into kotlin file - assign 3
object WeatherRepository {
    //Firestore
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("Users")
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var weatherList: MutableList<WeatherModel>? = null

    // week 9 api code
    // creates OkHttpClient (sends http requests)
    private val client = OkHttpClient()

    //    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    @JvmStatic
    fun get(city: String?, callback: Callback) {
        //manual http link
        // The endpoint is: GET https://api.weatherapi.com/v1/current.json?key={key}&q={city}&aqi=no
        // https://api.weatherapi.com/v1/current.json?key=4b1c28ddf81a49d5a6c155408261107&q=Toronto&aqi=no

        // assign 3 - updated with Kotlin
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.weatherapi.com")
            .addPathSegment("v1")
            .addPathSegment("current.json")
            .addQueryParameter("key", "4b1c28ddf81a49d5a6c155408261107")
            .addQueryParameter("q", city)
            .addQueryParameter("aqi", "no")
            .build()
        // request: GET
        val request = Request.Builder()
            .url(url)
            .build()
        // cleint call
        client.newCall(request).enqueue(callback)
    }

    // geocoding api (followed weatherapi code layout)
    fun dynamicSearch(city: String?, callback: Callback) {
        // Endpoint: GET https://geocoding-api.openmeteo.com/v1/search?name={query}&count=10&language=en&format=json
        // changed {query} to $city to input the city requested apon the api call
        val url =
            "https://geocoding-api.open-meteo.com/v1/search?name=$city&count=10&language=en&format=json"
        // request: GET
        val request = Request.Builder()
            .url(url)
            .build()
        // cleint call
        client.newCall(request).enqueue(callback)
    }


    // Firestore funs

    // SavedFrag function
    fun retrieveSavedCities(
        uid: String?, onSuccess: (ArrayList<WeatherModel>) ->
        Unit,
        onFailure: () -> Unit
    ) {
        if (uid != null) {
            collectionReference
                .document(uid)
                .collection("SavedCities")
                .get()
                .addOnSuccessListener { result ->
                    val weatherList = ArrayList<WeatherModel>()
                    for (document in result) {
                        val savedCity = document.toObject(SavedCityModel::class.java)

                        if (savedCity != null) {
                            val weather = WeatherModel()

                            weather.setCity(savedCity.getCityName());
                            weather.setRegion(savedCity.getRegion());
                            weather.setCountry(savedCity.getCountry());
                            weather.setLatitude(savedCity.getLatitude());
                            weather.setLongitude(savedCity.getLongitude());

                            weatherList.add(weather);
                        }
                    }
                    onSuccess(weatherList)
                }.addOnFailureListener { onFailure() }
        } else {
            onFailure()
        }
    }

    // SavedFrag and WeatherDetail function
    fun unsaveCity(
        uid: String?, city: String?, country: String?, onSuccess: () -> Unit, onFailure:
            () -> Unit
    ) {
        if (uid != null) {
            collectionReference
                .document(uid)
                .collection("SavedCities")
                .document("$city, $country")
                .delete()
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onFailure() }
        }
    }

    // WeatherDetail function
    fun checkCityIfSaved(
        uid: String, city: String, country: String?, onSuccess: (Boolean)
        -> Unit, onFailure:
            () -> Unit
    ) {
        collectionReference
            .document(uid)
            .collection("SavedCities")
            .document(city + ", " + country)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // City is saved
                    onSuccess(true)
                } else {
                    onSuccess(false)
                }
            }
            .addOnFailureListener { onFailure() }
    }

    // WeatherDetail function
    fun saveCity(
        uid: String?, city: String?, country: String, savedCity: SavedCityModel, onSuccess: ()
        -> Unit,
        onFailure: () -> Unit
    ) {

        // save data into the variabels above
        collectionReference
            .document(uid!!) // saves to users account
            .collection("SavedCities") // creates collection called 'SavedCities' to
            // organize the cities saved
            .document(city + ", " + country) // show city name
            .set(savedCity) // show city details
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure()}
    }
}
