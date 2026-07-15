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
                    JSONObject condition = current.getJSONObject("condition");

                    String strCity = location.getString("name");
                    String strRegion = location.getString("region");
                    String strTempC = current.getString("temp_c") + "°C";
                    String strTempF = current.getString("temp_f") + "°F";
                    String strWeatherCondition = condition.getString("text");
                    String strHumidity = current.getString("humidity") + "%";
                    String strWind = current.getString("wind_kph") + " kph";
                    String strFeelsLikeC = current.getString("feelslike_c") + "°C";
                    String strFeelsLikeF = current.getString("feelslike_f") + "°F";
                    String strWindChillC = current.getString("windchill_c") + "°C";
                    String strWindChillF = current.getString("windchill_f") + "°F";
                    String strUvIndex = current.getString("uv");

                    weatherModel.setCity(strCity);
                    weatherModel.setRegion(strRegion);
                    weatherModel.setWeatherCondition(strWeatherCondition);
                    weatherModel.setTempC(strTempC);
                    weatherModel.setTempF(strTempF);
                    weatherModel.setHumidity(strHumidity);
                    weatherModel.setWind(strWind);
                    weatherModel.setFeelsLikeC(strFeelsLikeC);
                    weatherModel.setFeelsLikeF(strFeelsLikeF);
                    weatherModel.setWindChillC(strWindChillC);
                    weatherModel.setWindChillF(strWindChillF);
                    weatherModel.setUvIndex(strUvIndex);

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
