package com.example.weatherpal.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherpal.model.WeatherModel;
import com.example.weatherpal.repository.WeatherRepository;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class WeatherViewModel extends ViewModel {
    // followed week 9 api code
    WeatherModel weatherModel = new WeatherModel();

    private final MutableLiveData<WeatherModel> weatherData = new MutableLiveData<WeatherModel>();
    public LiveData<WeatherModel> getWeatherData() {
        return weatherData;
    }

    // refresh
    public void Refresh(String city){
        WeatherRepository.get(city, new okhttp3.Callback(){

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                Log.i("tag", responseData); // not sure if needed
                JSONObject json = null;
                try{
                    json = new JSONObject(responseData);

                    JSONObject location = json.getJSONObject("location");
                    JSONObject current = json.getJSONObject("current");

                    String strCity = location.getString("name");
                    String strTempC = current.getString("temp_c") + "°C";

                    weatherModel.setCity(strCity);
                    weatherModel.setTempC(strTempC);

                    weatherData.postValue(weatherModel);

                } catch (Exception e){
                    Log.e("tag", "Error while parsing JSON: ", e);
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("tag", "onFailure"); // not sure if needed
            }
        });
    }
}
