package com.example.weatherpal.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherpal.databinding.FragmentSearchBinding;
import com.example.weatherpal.viewmodel.WeatherViewModel;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private WeatherViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
        // binding
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        binding.londonCard.setOnClickListener(v-> openCityDetails("London"));
        binding.torontoCard.setOnClickListener(v-> openCityDetails("Toronto"));
        binding.tokyoCard.setOnClickListener(v-> openCityDetails("Tokyo"));
        binding.sydneyCard.setOnClickListener(v-> openCityDetails("Sydney"));
        binding.newYorkCard.setOnClickListener(v-> openCityDetails("New York"));

        // api connection
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // set temp and weather condition using api response data
        viewModel.getWeatherData().observe(this, weatherData -> {
            binding.temp1.setText(weatherData.getTempC());
            binding.weather1.setText((weatherData.getWeatherCondition()));
        });

        // return
        return binding.getRoot();
    }

    // function to take user to the selected city's details page
    private void openCityDetails(String city){
        Intent intent = new Intent(requireActivity(), WeatherDetailActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }
}