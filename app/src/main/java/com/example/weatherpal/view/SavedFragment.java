package com.example.weatherpal.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherpal.R;
import com.example.weatherpal.model.SavedCityModel;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class SavedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false);
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