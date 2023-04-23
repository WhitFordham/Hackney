package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signUp = findViewById(R.id.button3);
        Button signIn = findViewById(R.id.button2);
        Button addRideOffer = findViewById(R.id.button4);
        Button addRideRequest = findViewById(R.id.button5);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email = "email@email.net";
        String password = "Shrenk";

        signUp.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);

            /*
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            "Registered user: " + email,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Sign up failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }); */
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

        addRideOffer.setOnClickListener(view -> {
            String dateAndTime = "January 4, 1245, 7:45";
            String startLocation = "Athens";
            String endLocation = "Atlanta";
            double price = 56.00;
            final Ride rideOffer = new Ride(dateAndTime, startLocation, endLocation, price);
            DatabaseReference reference = database.getReference("rideOffers");

            reference.push().setValue(rideOffer).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Ride Offer created",
                        Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(error -> {
                Toast.makeText(getApplicationContext(), "Failed to create a ride offer",
                        Toast.LENGTH_SHORT).show();
            });
    });

        addRideRequest.setOnClickListener(view -> {
            String dateAndTime = "March 22, 1785, 4:30";
            String startLocation = "Birmingham";
            String endLocation = "Montreal";
            double price = 275.00;
            final Ride rideRequest = new Ride(dateAndTime, startLocation, endLocation, price);
            DatabaseReference reference = database.getReference("rideRequests");

            reference.push().setValue(rideRequest).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Ride Request created",
                        Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(error -> {
                Toast.makeText(getApplicationContext(), "Failed to create a ride request",
                        Toast.LENGTH_SHORT).show();
            });
        });
}
}