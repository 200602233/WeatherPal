package com.example.weatherpal.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherpal.LoginActivity;
import com.example.weatherpal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // assign 3
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // handler to open MainActivity after 2 seconds. (will update after class)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){
            @Override
            public void run(){
//                Intent intent = new Intent(SplashActivity.this, Login.class);
//                startActivity(intent);
//                finish();
                // assign 3 code:
                if(currentUser != null){ //skip login if user has account
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else{ //send to login if not logged in
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);
    }
}