package com.example.weatherpal.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.weatherpal.BuildConfig;
import com.example.weatherpal.R;
import com.example.weatherpal.databinding.FragmentSettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    // Temperature unit preference must be persisted using SharedPreferences and applied globally
    // — all temperature displays must respect the user's choice
    private SharedPreferences preferences;

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

        // Preferences (may change after classes - did we learn this ?)
        //https://developer.android.com/reference/android/content/SharedPreferences
        // https://developer.android.com/develop/ui/views/components/radiobutton
        // https://www.youtube.com/watch?v=uUyZyws64eA - they use kotlin not java but based off it
        // boolean to listen to see if user changes temp and set the preference
        SharedPreferences preferences = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);

        // get value for isSettingsCelsius and dynamically assign
        // in this case, we're going to start with Celsius as true, and this will be reflected on weather detail page as well
        boolean isSettingsCelsius = preferences.getBoolean("cel", true);
        // assign binding 'isChecked' value based on which radio button has been pressed
        binding.tempC.setChecked(isSettingsCelsius);
        binding.tempF.setChecked(!isSettingsCelsius);

        /* gonna comment this bit out cause block above handles that logic now
        // Temp
        boolean tempChanged = preferences.getBoolean("cel", false);
        // checks to see what temp was clicked
        if (tempChanged) {
            binding.tempF.setChecked(true);
        } else {
            binding.tempC.setChecked(true);
        }*/

        //applies the Temp chosen
        binding.tempRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.tempF.getId()) {
                preferences.edit()
                        .putBoolean("cel", false)
                        .apply();
                Toast.makeText(requireContext(), "Fahrenheit was chosen!", Toast.LENGTH_SHORT).show();
            }

            else if (checkedId == binding.tempC.getId()) {
                preferences.edit()
                        .putBoolean("cel", true)
                        .apply();
                Toast.makeText(requireContext(), "Celsius was chosen!", Toast.LENGTH_SHORT).show();
            }
        });

        // Theme
        boolean darkTheme = preferences.getBoolean("darkTheme", false);
        //checks what theme was clicked
        if (darkTheme) {
            binding.darkTheme.setChecked(true);
        }
        else {
            binding.lightTheme.setChecked(true);
        }

        // sets the theme chosen
        binding.themeRadio.setOnCheckedChangeListener((group, checkedId) -> {

            SharedPreferences.Editor editor = preferences.edit();
            // create a boolean and saved in preferences to track whether theme was switched
            boolean themeSwitched = preferences.getBoolean("themeSwitched", false);

            // applies theme
            if (checkedId == R.id.lightTheme) {
                editor.putBoolean("darkTheme", false);
                editor.putBoolean("themeSwitched", true);
                // this is the line that basically restarts the app/main activity
                // it sends us back to settings via main activity
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                Toast.makeText(requireContext(), "Light Mode Selected", Toast.LENGTH_SHORT).show();
            }
            if (checkedId == R.id.darkTheme) {
                editor.putBoolean("darkTheme", true);
                editor.putBoolean("themeSwitched", true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                Toast.makeText(requireContext(), "Dark Mode Selected!", Toast.LENGTH_SHORT).show();
            }
            editor.apply();
        });

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