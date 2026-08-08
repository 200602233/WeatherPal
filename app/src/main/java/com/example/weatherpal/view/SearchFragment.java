package com.example.weatherpal.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherpal.R;
import com.example.weatherpal.databinding.FragmentSearchBinding;
import com.example.weatherpal.model.WeatherModel;
import com.example.weatherpal.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.os.Looper;

public class SearchFragment extends Fragment implements ItemClickListener{

    private FragmentSearchBinding binding;
    private WeatherViewModel viewModel;
    List<WeatherModel> weatherList;
    // adapater for onclick
    MyAdapter myAdapter;

    // assign 3
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
        // binding
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        // data source Assign 2 addition
        weatherList = new ArrayList<>();
//        WeatherModel place1 = new WeatherModel(R.drawable.rain_icon, "Toronto", "Ontario, Canada", 43.6667, -79.4167);
//        WeatherModel place2 = new WeatherModel(R.drawable.rain_icon, "Montreal", "Québec, Canada", 45.5, -73.5833);
//        WeatherModel place3 = new WeatherModel(R.drawable.rain_icon, "Winnipeg", "Manitoba, Canada", 49.8833, -97.1667);
//
//        // add to list
//        weatherList.add(place1);
//        weatherList.add(place2);
//        weatherList.add(place3);


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

        // search
        // same get() layout as the one in WeatherDetailActivity for getWeatherDetail() from
        // getLiveData in ViewModel
        viewModel.getDynamicSearchResults().observe(getViewLifecycleOwner(), results -> {
            // clears list (also clears list after user goes back from details activity, could
            // change that later)
            weatherList.clear();
            // shows all results that were searched
            weatherList.addAll(results);
            // tells recyclerview that list has been changed and refreshes
            myAdapter.notifyDataSetChanged();

            // if zero results show emptyMessage
            if(results.size() == 0){
                binding.emptyMessage.setVisibility(View.VISIBLE);
            } else{
                binding.emptyMessage.setVisibility(View.GONE);
            }
        });

        //search + delay (MIN 300 ms)
        // web-links i read about the addTextChangedListenr
        // https://www.geeksforgeeks.org/android/ontextchangedlistener-in-android/
        // https://developer.android.com/reference/android/text/TextWatcher
        binding.searchBar.addTextChangedListener(new TextWatcher() {
            // leave empty unless we need or want code but dont think need
            @Override
            public void afterTextChanged(Editable s) {}
            // leave empty unless we need or want code but dont think need
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // took from splash and re-coded to fit
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run(){
                        // searches after 3 characters (idk thought cool but could chnage)
                        if (s.length() >= 3){
                            viewModel.dynamicSearch(s.toString());
                        }
                    }
                }, 500); // wait 500 ms
            }
        });

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

        // geo info
        intent.putExtra("country", cityName.getCountry());
        intent.putExtra("latitude", cityName.getLatitude());
        intent.putExtra("longitude", cityName.getLongitude());
        startActivity(intent);
    }

    // function to take user to the selected city's details page
//    private void openCityDetails(String city){
//        Intent intent = new Intent(requireActivity(), WeatherDetailActivity.class);
//        intent.putExtra("city", city);
//        startActivity(intent);
//    }
}