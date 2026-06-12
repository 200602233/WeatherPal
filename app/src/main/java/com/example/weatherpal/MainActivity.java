package com.example.weatherpal;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.zip.Inflater;
import com.example.weatherpal.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // generated code
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        // BottomNavView connection (may have to update after Thursday class)

        switchFragment(new SearchFragment());

        //Colour binding to show active
        binding.bottomNavigation.setItemIconTintList(
                AppCompatResources.getColorStateList(this, R.color.bottom_nav_colors)
        );

        binding.bottomNavigation.setItemTextColor(
                AppCompatResources.getColorStateList(this, R.color.bottom_nav_colors)
        );

        binding.bottomNavigation.setSelectedItemId(R.id.searchBtn);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.searchBtn) {
                binding.headerTitle.setText("WeatherPal"); //default
                switchFragment(new SearchFragment());
                return true;
            } else if (item.getItemId() == R.id.savedBtn) {
                binding.headerTitle.setText("Saved Cities");
                switchFragment(new SavedFragment());
                return true;
            } else if (item.getItemId() == R.id.settingsBtn) {
                binding.headerTitle.setText("Settings");
                switchFragment(new SettingsFragment());
                return true;
            }

            return false;
        });
    }

    // Frag function to show frag view in contraint layout
    private void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragArea, fragment);
        ft.commit();
    }
}