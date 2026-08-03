package com.example.weatherpal.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherpal.LoginActivity;
import com.example.weatherpal.databinding.FragmentSettingsBinding;
import com.example.weatherpal.BuildConfig;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_settings, container, false);

        // binding
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        // Account
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // same we did in splash
        binding.user.setText(user.getEmail());

        // Preferences


        // About
        // set version by using BuildCOnfig.VERSION_NAME
        binding.versionTxt.setText("Version " + BuildConfig.VERSION_NAME);

        // Actions
        // code from week 4 Intent Slide 14
        binding.feedback.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"help@weatherpal.ca"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                    "WeatherPal — Feedback Suggestion");
            // teachers suggestion : Use try/catch as an alternative to resolveActivity() on Android 11+ (API 30+).
            try{
                startActivity(emailIntent);
            } catch (ActivityNotFoundException e){
                Toast.makeText(requireContext(),
                        "No email app found :(",
                        Toast.LENGTH_SHORT).show();
            }
        });


        // code from week 4-Intent slide 15
        /*
            Assignment Tip
                Link to your own GitHub repo. The URL can be anything
                valid — the marker checks the intent fires.
         */
        // github intent
        binding.github.setOnClickListener(v ->{
            String url = "https://github.com/200602233/WeatherPal";
            Intent browserIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
            );
            // teacher note::::
        // Guard against no browser being installed
            try {
                startActivity(browserIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(
                        requireContext(),
                        "No browser app found :(",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // code from week 4-Intent slide 16
        // share app intent
        binding.shareApp.setOnClickListener(v ->{
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain"); // MIME type (teacher note)
            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "🌤 WeatherPal, the most accurate weather app - https://google.com"
            );
        // user picks the app to share to
            Intent chooser = Intent.createChooser(
                    shareIntent,
                    "Share WeatherPal ..."
            );
            // teachers note - Guard: show Toast if nothing handles ACTION_SEND
            // added requireActvivty so it would properly call getPackageManager() isnce we are
            // using a fragment not activity
            if (shareIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(chooser);
            } else{
                Toast.makeText(requireContext(),
                        "Sharing errored :(",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // sign out user (update if different)
        binding.signOutBtn.setOnClickListener(v ->{
            FirebaseAuth.getInstance().signOut(); //sign out
            // go back to login
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}