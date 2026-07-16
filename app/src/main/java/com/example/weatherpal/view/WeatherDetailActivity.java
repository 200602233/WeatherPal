package com.example.weatherpal.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherpal.R;
import com.example.weatherpal.databinding.ActivityWeatherDetailBinding;
import com.example.weatherpal.viewmodel.WeatherViewModel;

public class WeatherDetailActivity extends AppCompatActivity {

    private ActivityWeatherDetailBinding binding;
    private WeatherViewModel viewModel;

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
//        setToolBar(city);
//        setWeatherDetails(city);

        // api connection to fill out data (commented out past functions)
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        viewModel.getWeatherData().observe(this, weatherData -> {
            // toolbar binding
            binding.toolbar.setTitle(weatherData.getCity());
            binding.toolbar.setSubtitle(weatherData.getRegion());

            // cards
            binding.weatherCondition.setText(weatherData.getWeatherCondition());
            binding.tempC.setText(weatherData.getTempC());
            binding.tempF.setText(weatherData.getTempF());
            binding.humidity.setText(weatherData.getHumidity());
            binding.wind.setText(weatherData.getWind());
            binding.feelsLikeC.setText(weatherData.getFeelsLikeC());
            binding.feelsLikeF.setText(weatherData.getFeelsLikeF());
            binding.windChillC.setText(weatherData.getWindChillC());
            binding.windChillF.setText(weatherData.getWindChillF());
            binding.uvIndex.setText(weatherData.getUvIndex());
        });

        // added this so api would properly show data
        if (city != null && !city.isEmpty()) {
            viewModel.Refresh(city);
        }

        // progress bar visibility state based off code from week9 slide 13
        // modified the example syntax from kotlin (i think?) to java instead
        // call our getIsLoading method from viewModel (i.e. WeatherViewModel class)
        // observe the current state of our WeatherDetailActivity and display/hide bar accordingly
        viewModel.getIsLoading().observe(this, isLoading -> {
            // bind the progress bar by referencing it from activity_weather_data.xml
            // change the progress bar visibility based on whether the WeatherDetailActivity is still loading
            // if API call in progress, make bar visible; if not in progress, then make it not visible
            // ternary operator syntax: condition ? value_if_true : value_if_false
            binding.progressBarIndeterminate.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // error message - change after class if we learn it
        viewModel.getErrorMessage()
                .observe(
                        this,
                        message -> {

                            Toast.makeText(
                                    this,
                                    message,
                                    Toast.LENGTH_LONG
                            ).show();

                        });

        //back btn
        binding.backBtn.setOnClickListener(view -> backBtnAction());
    }

    // back btn
    public void backBtnAction(){
        Intent intent = new Intent(WeatherDetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
    // function to change header/toolbar title to display selected city
//    private void setToolBar(String city){
//        if(city.equals("London")){
//            binding.toolbar.setTitle("London");
//            binding.toolbar.setSubtitle("City of London, Greater London");
//        }
//        else if(city.equals("Toronto")){
//            binding.toolbar.setTitle("Toronto");
//            binding.toolbar.setSubtitle("Ontario");
//        }
//        else if(city.equals("Tokyo")){
//            binding.toolbar.setTitle("Tokyo");
//            binding.toolbar.setSubtitle("Tokyo-to");
//        }
//        else if(city.equals("Sydney")){
//            binding.toolbar.setTitle("Sydney");
//            binding.toolbar.setSubtitle("New South Wales");
//        }
//        else if(city.equals("New York")){
//            binding.toolbar.setTitle("New York");
//            binding.toolbar.setSubtitle("New York State");
//        }
//    }
//
//    // function to display weather details for the selected city
//    // for now we're using hardcoded values
//    private void setWeatherDetails(String city){
//        String celsius = "℃";
//        String fahrenheit = "℉";
//
//        if(city.equals("London")){
//            binding.weatherCondition.setText("Cloudy");
//            binding.tempC.setText("14" + celsius);
//            binding.tempF.setText("57" + fahrenheit);
//            binding.humidity.setText("78%");
//            binding.wind.setText("18 kph");
//            binding.feelsLike.setText("12" + celsius);
//            binding.uvIndex.setText("2 - Low");
//        }
//        else if(city.equals("Toronto")){
//            binding.weatherCondition.setText("Sunny");
//            binding.tempC.setText("22" + celsius);
//            binding.tempF.setText("72" + fahrenheit);
//            binding.humidity.setText("83%");
//            binding.wind.setText("16 kph");
//            binding.feelsLike.setText("26" + celsius);
//            binding.uvIndex.setText("8 - Very High");
//        }
//        else if(city.equals("Tokyo")){
//            binding.weatherCondition.setText("Clear");
//            binding.tempC.setText("28" + celsius);
//            binding.tempF.setText("82" + fahrenheit);
//            binding.humidity.setText("64%");
//            binding.wind.setText("10 kph");
//            binding.feelsLike.setText("32" + celsius);
//            binding.uvIndex.setText("7 - High");
//        }
//        else if(city.equals("Sydney")){
//            binding.weatherCondition.setText("Sunny");
//            binding.tempC.setText("20" + celsius);
//            binding.tempF.setText("68" + fahrenheit);
//            binding.humidity.setText("56%");
//            binding.wind.setText("15 kph");
//            binding.feelsLike.setText("25" + celsius);
//            binding.uvIndex.setText("5 - Moderate");
//        }
//        else if(city.equals("New York")){
//            binding.weatherCondition.setText("Rainy");
//            binding.tempC.setText("11" + celsius);
//            binding.tempF.setText("52" + fahrenheit);
//            binding.humidity.setText("63%");
//            binding.wind.setText("9 kph");
//            binding.feelsLike.setText("15" + celsius);
//            binding.uvIndex.setText("6 - High");
//        }
//    }
}