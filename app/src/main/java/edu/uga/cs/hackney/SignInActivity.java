package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText editEmail = findViewById(R.id.editText2);
        EditText editPassword = findViewById(R.id.editText3);

        Button signIn = findViewById(R.id.button12);
        signIn.setOnClickListener(view -> {
            auth = FirebaseAuth.getInstance();
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            Intent intent = new Intent( SignInActivity.this, RideManagementActivity.class );

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity( intent );
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