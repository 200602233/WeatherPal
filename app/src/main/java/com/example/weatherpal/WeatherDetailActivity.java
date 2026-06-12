package com.example.weatherpal;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherpal.databinding.ActivityWeatherDetailBinding;

public class WeatherDetailActivity extends AppCompatActivity {

    private ActivityWeatherDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //binding
        binding = ActivityWeatherDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String city = getIntent().getStringExtra("city");
        setToolBar(city);
        setWeatherDetails(city);
    }
    private void setToolBar(String city){
        if(city.equals("London")){
            binding.toolbar.setTitle("London");
            binding.toolbar.setSubtitle("City of London, Greater London");
        }
        else if(city.equals("Toronto")){
            binding.toolbar.setTitle("Toronto");
            binding.toolbar.setSubtitle("Ontario");
        }
    }

    private void setWeatherDetails(String city){
        if(city.equals("London")){
            binding.weatherCondition.setText("");
            binding.tempC.setText("");
            binding.tempF.setText("");
            binding.humidity.setText("");
            binding.wind.setText("");
            binding.feelsLike.setText("");
            binding.uvIndex.setText("");
        } else if(city.equals("Toronto")){
            binding.weatherCondition.setText("");
            binding.tempC.setText("");
            binding.tempF.setText("");
            binding.humidity.setText("");
            binding.wind.setText("");
            binding.feelsLike.setText("");
            binding.uvIndex.setText("");
        }
    }
}