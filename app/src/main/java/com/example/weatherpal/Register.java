package com.example.weatherpal;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherpal.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // bidning setContent
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // week 11 referred to for code below:

        mAuth = FirebaseAuth.getInstance();

        //hard coded name
        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser("z@z.com", "password");
            }
        });
    }

    // regsiter
    private void registerUser(String email, String password) {
        Toast.makeText(Register.this, "registerUser.", Toast.LENGTH_SHORT).show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(Register.this, "registerUser Pass." +user.getUid(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Register.this, "registerUser Failed." + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}