package com.example.weatherpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherpal.databinding.ActivityRegisterBinding;
import com.example.weatherpal.view.MainActivity;
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

        binding.regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // same code for login and password, just different id's

                // grab users inputs
                // IF PASS IS UNDER 6 CHARS, IT WILL FAIL
                String userEmail = binding.regEmail.getText().toString().trim();
                String userPassword = binding.regPassword.getText().toString().trim();

                // apply information to registering the user
                registerUser(userEmail, userPassword);
            }
        });
    }

    // regsiter
    private void registerUser(String email, String password) {
        Toast.makeText(Register.this, "Registering User...", Toast.LENGTH_SHORT).show();

        // when running, we noticed that the firebase does not allow passwords to be under
        // 6characters, therefore, we added an if statement to allow user to know why they could
        // not register
        if (password.length() < 6){
            Toast.makeText(Register.this, "Password must be at least 6 Characters", Toast.LENGTH_SHORT).show();
            // stops before it shows other toasts below
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(Register.this, "User Registered!", Toast.LENGTH_SHORT).show();
                Intent intObj = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intObj);
                finish();
            } else {
                Toast.makeText(Register.this, "ERROR Registering User! " + task.getException(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}