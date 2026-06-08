package com.example.weatherpal;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

        // search btn
        binding.searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switchFragment(new SearchFragment());
            }
        });

        // saved btn
        binding.savedBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switchFragment(new SearchFragment());
            }
        });

        // settings btn
        binding.settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switchFragment(new SearchFragment());
            }
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