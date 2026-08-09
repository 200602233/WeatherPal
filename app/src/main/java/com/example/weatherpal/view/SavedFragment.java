package com.example.weatherpal.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherpal.R;
import com.example.weatherpal.databinding.FragmentSavedBinding;
import com.example.weatherpal.databinding.FragmentSearchBinding;
import com.example.weatherpal.model.SavedCityModel;
import com.example.weatherpal.model.WeatherModel;
import com.example.weatherpal.viewmodel.WeatherViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class SavedFragment extends Fragment {

    // binding, firestore and auth
    private FragmentSavedBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("Users");
    FirebaseAuth auth = FirebaseAuth.getInstance();

    //weather mdoel
    private final List<SavedCityModel> savedCityModel = new ArrayList<>();

    private final List<WeatherModel> weatherList = new ArrayList<>();

    private MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(weatherList, true);
        binding.recyclerView.setAdapter(myAdapter);


        // retrieve saved cities
        retrieveSavedCities();

        // return
        return binding.getRoot();
    }

    // retrieve any saved locations
    private void retrieveSavedCities(){
        // firebase user auth
        FirebaseUser user = auth.getCurrentUser();

        // if user null
        if (user != null) {
            // same as SavedLocations delete method?
            String uid = user.getUid();
            collectionReference
                    .document(uid)
                    .collection("SavedCities")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {

                        //removes old list
                        weatherList.clear();
                        savedCityModel.clear();

                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {

                            SavedCityModel savedCity =
                                    document.toObject(SavedCityModel.class);

                            if (savedCity != null) {
                                savedCityModel.add(savedCity);
                            }
                        }

                        for(SavedCityModel savedCity : savedCityModel){
                            WeatherModel weather = new WeatherModel();

                            weather.setWeatherIcon(savedCity.getCityIcon());
                            weather.setCity(savedCity.getCityName());
                            weather.setRegion(savedCity.getRegion());
                            weather.setCountry(savedCity.getCountry());
                            weather.setLatitude(savedCity.getLatitude());
                            weather.setLongitude(savedCity.getLongitude());
                            weather.setActionIcon(savedCity.getActionIcon());

                            weatherList.add(weather);
                        }

                        myAdapter.notifyDataSetChanged();

                        if(weatherList.isEmpty()){
                            binding.emptyImageView.setVisibility(View.VISIBLE);
                            binding.emptyMessage1.setVisibility(View.VISIBLE);
                            binding.emptyMessage2.setVisibility(View.VISIBLE);
                            binding.recyclerView.setVisibility(View.GONE);
                        } else{
                            binding.emptyImageView.setVisibility(View.GONE);
                            binding.emptyMessage1.setVisibility(View.GONE);
                            binding.emptyMessage2.setVisibility(View.GONE);
                            binding.recyclerView.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(e->{
                        Toast.makeText(requireContext(), "Error retrieving cities!", Toast.LENGTH_SHORT).show();
                    });
        }
    }

    // delete city
    private void deleteSavedCity(){
        // copied from WeatherDetials, edited tho

        // firebase user auth
        FirebaseUser user = auth.getCurrentUser();

        // if user null
//        if (user != null) {
//            // same as SavedLocations delete method?
//            String uid = user.getUid();
//            collectionReference
//                    .document(uid)
//                    .collection("SavedCities")
//                    .document(city + ", " + country)
//                    .delete()
//                    .addOnSuccessListener(documentReference->{
//                        binding.bookmarked.setVisibility(View.GONE);
//                        binding.emptyBookmark.setVisibility(View.VISIBLE);
//                        // toast
//                        Toast.makeText(this, "City saved", Toast.LENGTH_SHORT).show();
//                    }).addOnFailureListener(e ->{
//                        //toast
//                        Toast.makeText(this, "Failed to save!", Toast.LENGTH_SHORT).show();
//                    });
//        } else{
//            // user not found
//            Toast.makeText(this, "User Not Found!", Toast.LENGTH_SHORT).show();
//        }
    }


    //testing
//    private void getCity(){
//        collectionReference.get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    String data = "";
//                    for (QueryDocumentSnapshot document : queryDocumentSnapshots){
//                        // process the retrieved documents
//                        SavedCityModel savedCity = document.toObject(SavedCityModel.class);
//                        String city = document.getString("city");
//                        String country = document.getString("country");
//
//                        data += "City: " + city + ", Country: " + country + "\n";
//
//                        // do something with each document
//                        Toast.makeText(this, "Document: " + document.getId(), Toast.LENGTH_SHORT).show();
//                    }
//                    // display all retrieved data
//                    Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
//                    binding.saveText.setText(data);
//                })
//                .addOnFailureListener(e -> {
//                    // handle error
//                    Toast.makeText(this, "Error retrieving documents", Toast.LENGTH_SHORT).show();
//                });
//
//    }
}