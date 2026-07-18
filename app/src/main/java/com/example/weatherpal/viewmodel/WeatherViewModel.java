package com.example.weatherpal.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherpal.R;
import com.example.weatherpal.model.WeatherModel;
import com.example.weatherpal.repository.WeatherRepository;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

// imports in order to replaced OnCleared() code from MVVM lecture
// seems like they work in unison
// need to be able to pass in a looper parameter to the handler object or get a warning about handler being deprecated
import android.os.Handler;
import android.os.Looper;
// also including log class so we can confirm via logcat that oncleared() has been called
import android.util.Log;

public class WeatherViewModel extends ViewModel {
    // followed week 9 api code
    WeatherModel weatherModel = new WeatherModel();

    // handler and runnable for use in OnCleared() method override
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;
    // define TAG parameter in the logcat method so we know where message is coming from
    private static final String TAG = "WeatherViewModel";

    // api weather data
    private final MutableLiveData<WeatherModel> weatherData = new MutableLiveData<WeatherModel>();
    public LiveData<WeatherModel> getWeatherData() {
        return weatherData;
    }

    // error message
    private final MutableLiveData<String> error = new MutableLiveData<String>();
    public LiveData<String> getErrorMessage() {
        return error;
    }

    // isLoading state (i.e. whether we've finished fetching the API data)
    // initial reference: week9 slide 12
    // using MutableLiveData basically as a container to hold data related to the api call
    // LiveData class also as a container; LiveData is ready only, MutableLiveData is read and write
    // MutableLiveData doesn't pull any api data itself
    // isLoading syntax modeled after the weatherData and error syntax above
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<Boolean>();
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // refresh
    public void Refresh(String city){
        // set our isLoading value to true before we make the API call
        /* using postValue() instead of setValue() because app was initially crashing with following logcat
        error - java.lang.IllegalStateException: Cannot invoke setValue on a background thread */
        isLoading.postValue(true);
        WeatherRepository.get(city, new okhttp3.Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.i("WeatherViewModel", responseData); // not sure if needed
                JSONObject json = null;
                try{
                    json = new JSONObject(responseData);
                    // use https://api.weatherapi.com/v1/current.json?key=4b1c28ddf81a49d5a6c155408261107&q=Toronto&aqi=no
                    // ^^ for reference
                    // JSON location in api
                    JSONObject location = json.getJSONObject("location"); //name, region,
                    // country, etc etc
                    JSONObject current = json.getJSONObject("current");
                    JSONObject condition = current.getJSONObject("condition");

                    // get data from api
                    String strCity = location.getString("name");
                    String strRegion = location.getString("region");
                    String strCountry = location.getString("country");
                    Double dblLatitude = location.getDouble("lat");
                    Double dblLongtitude = location.getDouble("lon");
                    String strTempC = current.getString("temp_c") + "°C";
                    String strTempF = current.getString("temp_f") + "°F";
                    String strWeatherCondition = condition.getString("text");
                    //Integer intWeatherIcon = condition.getInt("icon");
                    String strHumidity = current.getString("humidity") + "%";
                    String strWind = current.getString("wind_kph") + " kph";
                    String strFeelsLikeC = current.getString("feelslike_c") + "°C";
                    String strFeelsLikeF = current.getString("feelslike_f") + "°F";
                    String strWindChillC = current.getString("windchill_c") + "°C";
                    String strWindChillF = current.getString("windchill_f") + "°F";
                    String strUvIndex = current.getString("uv");

                    // display live data to the id locations
                    weatherModel.setCity(strCity);
                    weatherModel.setRegion(strRegion);
                    weatherModel.setCountry(strCountry);
                    weatherModel.setLatitude(dblLatitude);
                    weatherModel.setLongitude(dblLongtitude);
                    weatherModel.setWeatherCondition(strWeatherCondition);
                    //weatherModel.setWeatherIcon(intWeatherIcon);
                    // hardcoded the icon for now
                    weatherModel.setWeatherIcon(R.drawable.rain_icon);
                    weatherModel.setTempC(strTempC);
                    weatherModel.setTempF(strTempF);
                    weatherModel.setHumidity(strHumidity);
                    weatherModel.setWind(strWind);
                    weatherModel.setFeelsLikeC(strFeelsLikeC);
                    weatherModel.setFeelsLikeF(strFeelsLikeF);
                    weatherModel.setWindChillC(strWindChillC);
                    weatherModel.setWindChillF(strWindChillF);
                    weatherModel.setUvIndex(strUvIndex);

                    // set our isLoading value to false once all the weather data has been fetched
                    isLoading.postValue(false);
                    weatherData.postValue(weatherModel);

                } catch (Exception e){
                    // set our isLoading value to false if we get an error during API call
                    isLoading.postValue(false);
                    Log.e("WeatherViewModel", "Error while parsing JSON: ", e);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("WeatherViewModel", "Network Error. Request Failed :("); // not sure if needed
                // error message
                // - Show user-friendly error message if network call fails (DO NOT CRASH)
                // may update after class
                error.postValue("Network Connection Issue. Please Check Your Network.");
            }
        });
    }

    // based on code from MVVM lecture
    // called by the framework when this viewmodel's about to be destroyed
    // used to prevent memory leaks
    @Override
    protected void onCleared() {
        // when onCleared() is called be the framework, it calls our implementation here
        // which is to also call oncleared() on the parent class we are extending, i.e. ViewModel
        super.onCleared();
        // and then remove all current callback methods related to this viewmodel
        handler.removeCallbacks(runnable);
        // logcat message to confirm oncleared() was called
        // checked logcat and confirmed that pressing android studio back button does trigger this log message
        Log.i(TAG, "WeatherViewModel cleared.");
    }
}
