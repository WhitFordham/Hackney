package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signUp = findViewById(R.id.button3);
        Button signIn = findViewById(R.id.button2);

        auth = FirebaseAuth.getInstance();
        String email = "email@email.net";
        String password = "Shrenk";

        signUp.setOnClickListener(view -> {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Registered user: " + email,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Sign up failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        signIn.setOnClickListener(view -> {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   Toast.makeText(getApplicationContext(),
                           "Signed in successfully!", Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(getApplicationContext(), "Sign in failed.",
                           Toast.LENGTH_SHORT).show();
               }
            });
        });
    }
}