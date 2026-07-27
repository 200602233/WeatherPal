package com.example.weatherpal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherpal.databinding.ActivityLoginBinding;
import com.example.weatherpal.view.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    // firebase
    private FirebaseAuth mAuth;

    //binding
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // binding setCOntent
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // week 11 code referred to for below:
        mAuth = FirebaseAuth.getInstance();

        // binding for switching to register screen
        binding.registerNow.setOnClickListener(view -> {
            Intent intObj = new Intent(getApplicationContext(), Register.class);
            startActivity(intObj);
        });

        //login (hard coded user)
        binding.loginBtn.setOnClickListener(view -> {
            // grab users inputs
            String userEmail = binding.email.getText().toString().trim();
            String userPassword = binding.password.getText().toString().trim();

            // apply information to registering the user
            loginUser(userEmail, userPassword);
        });
    }
    private void loginUser(String email, String password) {
        Toast.makeText(this, "Loading User...", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "Successfully Logged-In", Toast.LENGTH_SHORT).show();
                Intent intObj = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intObj);
                finish();
            } else {
                Toast.makeText(Login.this, "Failed Logging-In." + task.getException(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}