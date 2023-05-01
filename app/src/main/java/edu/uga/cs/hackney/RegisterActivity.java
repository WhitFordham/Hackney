package edu.uga.cs.hackney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "RegisterActivity";

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.editText);
        passwordEditText = findViewById(R.id.editText5);

        Button registerButton = findViewById(R.id.button3);
        registerButton.setOnClickListener(view -> {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            if (email == null || password == null || email.length() == 0 || password.length() == 0) {
                Toast.makeText(getApplicationContext(), "Enter both an email and a password", Toast.LENGTH_SHORT).show();
                return;
            }

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Registered user: " + email, Toast.LENGTH_SHORT).show();

                    // Sign in success, update UI with the signed-in user's information
                    Log.d(DEBUG_TAG, "createUserWithEmail: success");

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    database.getReference("users").child(user.getUid()).child("points").setValue(150);

                    Intent intent = new Intent(RegisterActivity.this, SignInActivity.class);
                    startActivity(intent);

                } else {
                    Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                    Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}