package com.example.weatherpal.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherpal.R;
import com.example.weatherpal.databinding.FragmentSearchBinding;
import com.example.weatherpal.model.WeatherModel;
import com.example.weatherpal.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements ItemClickListener{

    private FragmentSearchBinding binding;
    private WeatherViewModel viewModel;
    List<WeatherModel> weatherList;
    // adapater for onclick
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
        // binding
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        // data source Assign 2 addition
        weatherList = new ArrayList<>();
        WeatherModel place1 = new WeatherModel(R.drawable.white_circle, "Toronto", "Ontario, Canada", 43.6667, -79.4167);
        WeatherModel place2 = new WeatherModel(R.drawable.white_circle, "Montreal", "Québec, Canada", 45.5, -73.5833);
        WeatherModel place3 = new WeatherModel(R.drawable.white_circle, "AnotherCity", "Province, Canada", 1, -1);

        // add to list
        weatherList.add(place1);
        weatherList.add(place2);
        weatherList.add(place3);


        // Assign 1 binding
//        binding.londonCard.setOnClickListener(v-> openCityDetails("London"));
//        binding.torontoCard.setOnClickListener(v-> openCityDetails("Toronto"));
//        binding.tokyoCard.setOnClickListener(v-> openCityDetails("Tokyo"));
//        binding.sydneyCard.setOnClickListener(v-> openCityDetails("Sydney"));
//        binding.newYorkCard.setOnClickListener(v-> openCityDetails("New York"));

        // api connection
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // set temp and weather condition using api response data
//        viewModel.getWeatherData().observe(this, weatherData -> {
//            binding.temp1.setText(weatherData.getTempC());
//            binding.weather1.setText((weatherData.getWeatherCondition()));
//        });

        // week 10 lesson
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(weatherList);
        binding.recyclerView.setAdapter(myAdapter);

        myAdapter.setClickListener(this);
        // return
        return binding.getRoot();
    }

    @Override
    public void onClick(View v, int pos) {
        Log.i("SearchFragment", "Click was received!");
        // need toast?
        WeatherModel cityName = weatherList.get(pos);
        // intent
        Intent intent = new Intent(requireActivity(), WeatherDetailActivity.class);
        intent.putExtra("city", cityName.getCity());
        startActivity(intent);
    }

    // function to take user to the selected city's details page
//    private void openCityDetails(String city){
//        Intent intent = new Intent(requireActivity(), WeatherDetailActivity.class);
//        intent.putExtra("city", city);
//        startActivity(intent);
//    }
}