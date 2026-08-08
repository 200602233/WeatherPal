package com.example.weatherpal.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherpal.R
import com.example.weatherpal.model.WeatherModel
import com.example.weatherpal.repository.WeatherRepository
import com.example.weatherpal.repository.WeatherRepository.get
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

// imports in order to replaced OnCleared() code from MVVM lecture
// seems like they work in unison
// need to be able to pass in a looper parameter to the handler object or get a warning about handler being deprecated
// also including log class so we can confirm via logcat that oncleared() has been called
// convert into kotlin file
class WeatherViewModel : ViewModel() {
    // followed week 9 api code
    var weatherModel: WeatherModel = WeatherModel()

    // handler and runnable for use in OnCleared() method override
    private val handler = Handler(Looper.getMainLooper())
    private val runnable: Runnable? = null

    // api weather data
    private val weatherData = MutableLiveData<WeatherModel?>()
    fun getWeatherData(): LiveData<WeatherModel?> {
        return weatherData
    }

    // geocoding api code
    private val dynamicSearchResults = MutableLiveData<ArrayList<WeatherModel>>()

    fun getDynamicSearchResults(): LiveData<ArrayList<WeatherModel>> {
        return dynamicSearchResults
    }


    // error message
    private val error = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = error

    // isLoading state (i.e. whether we've finished fetching the API data)
    // initial reference: week9 slide 12
    // using MutableLiveData basically as a container to hold data related to the api call
    // LiveData class also as a container; LiveData is ready only, MutableLiveData is read and write
    // MutableLiveData doesn't pull any api data itself
    // isLoading syntax modeled after the weatherData and error syntax above
    private val isLoading = MutableLiveData<Boolean?>()
    fun getIsLoading(): LiveData<Boolean?> {
        return isLoading
    }

    // refresh
    fun Refresh(city: String?) {
        // set our isLoading value to true before we make the API call
        /* using postValue() instead of setValue() because app was initially crashing with following logcat
        error - java.lang.IllegalStateException: Cannot invoke setValue on a background thread */
        isLoading.postValue(true)
        get(city, object : Callback {
            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body.string()
                Log.i("WeatherViewModel", responseData) // not sure if needed
                var json: JSONObject? = null
                try {
                    json = JSONObject(responseData)
                    // use https://api.weatherapi.com/v1/current.json?key=4b1c28ddf81a49d5a6c155408261107&q=Toronto&aqi=no
                    // ^^ for reference
                    // JSON location in api
                    val location = json.getJSONObject("location") //name, region,
                    // country, etc etc
                    val current = json.getJSONObject("current")
                    val condition = current.getJSONObject("condition")

                    // get data from api
                    val strCity = location.getString("name")
                    val strRegion = location.getString("region")
                    val strCountry = location.getString("country")
                    val dblLatitude = location.getDouble("lat")
                    val dblLongtitude = location.getDouble("lon")
                    val strTempC = current.getString("temp_c") + "°C"
                    val strTempF = current.getString("temp_f") + "°F"
                    val strWeatherCondition = condition.getString("text")
                    //Integer intWeatherIcon = condition.getInt("icon");
                    val strHumidity = current.getString("humidity") + "%"
                    val strWind = current.getString("wind_kph") + " kph"
                    val strFeelsLikeC = current.getString("feelslike_c") + "°C"
                    val strFeelsLikeF = current.getString("feelslike_f") + "°F"
                    val strWindChillC = current.getString("windchill_c") + "°C"
                    val strWindChillF = current.getString("windchill_f") + "°F"
                    val strUvIndex = current.getString("uv")

                    // display live data to the id locations
                    weatherModel.setCity(strCity)
                    weatherModel.setRegion(strRegion)
                    weatherModel.setCountry(strCountry)
                    weatherModel.setLatitude(dblLatitude)
                    weatherModel.setLongitude(dblLongtitude)
                    weatherModel.setWeatherCondition(strWeatherCondition)
                    //weatherModel.setWeatherIcon(intWeatherIcon);
                    // hardcoded the icon for now
                    weatherModel.setWeatherIcon(R.drawable.rain_icon)
                    weatherModel.setTempC(strTempC)
                    weatherModel.setTempF(strTempF)
                    weatherModel.setHumidity(strHumidity)
                    weatherModel.setWind(strWind)
                    weatherModel.setFeelsLikeC(strFeelsLikeC)
                    weatherModel.setFeelsLikeF(strFeelsLikeF)
                    weatherModel.setWindChillC(strWindChillC)
                    weatherModel.setWindChillF(strWindChillF)
                    weatherModel.setUvIndex(strUvIndex)

                    // set our isLoading value to false once all the weather data has been fetched
                    isLoading.postValue(false)
                    weatherData.postValue(weatherModel)
                } catch (e: Exception) {
                    // set our isLoading value to false if we get an error during API call
                    isLoading.postValue(false)
                    Log.e("WeatherViewModel", "Error while parsing JSON: ", e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.i("WeatherViewModel", "Network Error. Request Failed :(") // not sure if needed
                // error message
                // - Show user-friendly error message if network call fails (DO NOT CRASH)
                // may update after class
                error.postValue("Network Connection Issue. Please Check Your Network.")
            }
        })
    }

    // followed layout above from weatherapi
    fun dynamicSearch(city: String) {
        WeatherRepository.dynamicSearch(city,object : Callback {
            override fun onResponse(call: Call, response: Response) {
                // array list for weatehr
                val weatherList = ArrayList<WeatherModel>()
                val responseData = response.body.string()
                Log.i("WeatherViewModel-GEO", responseData) // not sure if needed
                var json: JSONObject? = null
                try {
                    json = JSONObject(responseData)

                    // reuslts for the search array list
                    val results = json.getJSONArray("results")

                    // shows max results, min is 5
                    for (i in 0 until results.length()){
                        // JSON for city to determine what shows
                        val city = results.getJSONObject(i)

                        // get vars from geo
                        val name = city.getString("name")
                        val region = city.getString("admin1")
                        val country = city.getString("country")
                        val latitude = city.getDouble("latitude")
                        val longitude = city.getDouble("longitude")

                        // add to list
                        // change icon later
                        weatherList.add(WeatherModel(R.drawable.rain_icon,name, region, country,
                                latitude, longitude))
                    }
                    dynamicSearchResults.postValue(weatherList)

                } catch (e: Exception) {
                    Log.e("WeatherViewModel-GEO", "Error while searching: ", e)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                // same network failure for page as weather deatils
                Log.i("WeatherViewModel", "Network Error. Request Failed :(") // not sure if needed
                // error message
                // - Show user-friendly error message if network call fails (DO NOT CRASH)
                // may update after class
                error.postValue("Network Connection Issue. Please Check Your Network.")
            }
        }
    )
}

    // based on code from MVVM lecture
    // called by the framework when this viewmodel's about to be destroyed
    // used to prevent memory leaks
    override fun onCleared() {
        // when onCleared() is called be the framework, it calls our implementation here
        // which is to also call oncleared() on the parent class we are extending, i.e. ViewModel
        super.onCleared()

        // and then remove all current callback methods related to this viewmodel
        //handler.removeCallbacks(runnable!!)

        // ASSIGN 3
        // replace handler with code below to properly run thsi file as kotlin
        runnable?.let { handler.removeCallbacks(it) }

        // logcat message to confirm oncleared() was called
        // checked logcat and confirmed that pressing android studio back button does trigger this log message
        Log.i(TAG, "WeatherViewModel cleared.")
    }

    companion object {
        // define TAG parameter in the logcat method so we know where message is coming from
        private const val TAG = "WeatherViewModel"
    }
}
