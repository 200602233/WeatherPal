package com.example.weatherpal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherpal.databinding.FragmentSearchBinding;
import com.example.weatherpal.databinding.FragmentSettingsBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_search, container, false);
        // binding
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        binding.londonCard.setOnClickListener(v-> openCityDetails("London"));
        binding.torontoCard.setOnClickListener(v-> openCityDetails("Toronto"));


        // return
        return binding.getRoot();
    }
    private void openCityDetails(String city){
        Intent intent = new Intent(requireActivity(), WeatherDetailActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }
}