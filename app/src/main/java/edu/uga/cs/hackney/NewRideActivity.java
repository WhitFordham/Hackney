package edu.uga.cs.hackney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;

public class NewRideActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText dateView;
    private EditText timeView;
    private EditText endLocationView;
    private EditText startLocationView;
    private EditText priceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        dateView = findViewById( R.id.editTextDate );
        timeView = findViewById( R.id.editTextTime );
        startLocationView = findViewById( R.id.editTextTextPostalAddress );
        endLocationView = findViewById( R.id.editTextTextPostalAddress2 );
        priceView = findViewById( R.id.editTextNumberDecimal );
        Button requestButton = findViewById(R.id.button10);
        Button offerButton = findViewById(R.id.button11);

        offerButton.setOnClickListener( view -> {
            String date = dateView.getText().toString();
            String time = timeView.getText().toString();
            String startLocation = startLocationView.getText().toString();
            String endLocation = endLocationView.getText().toString();
            Double price = Double.valueOf(priceView.getText().toString());
            final Ride rideOffer = new Ride(date + time, startLocation, endLocation, price);

            auth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference reference = database.getReference("rideOffers");

            reference.push().setValue(rideOffer).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Ride Offer created",
                        Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(error -> {
                Toast.makeText(getApplicationContext(), "Failed to create a ride offer",
                        Toast.LENGTH_SHORT).show();
            });

            FirebaseUser user = auth.getCurrentUser();
            database.getReference("users").child(user.getUid()).child("createdOffers")
                    .setValue(rideOffer);

            Intent intent = new Intent( NewRideActivity.this, ReviewOffersActivity.class );
            startActivity( intent );
        });

        requestButton.setOnClickListener( view -> {
            String date = dateView.getText().toString();
            String time = timeView.getText().toString();
            String startLocation = startLocationView.getText().toString();
            String endLocation = endLocationView.getText().toString();
            Double price = Double.valueOf(priceView.getText().toString());
            final Ride rideRequest = new Ride(date + time, startLocation, endLocation, price);

            auth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference reference = database.getReference("rideRequests");

            reference.push().setValue(rideRequest).addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Ride Request created",
                        Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(error -> {
                Toast.makeText(getApplicationContext(), "Failed to create a ride request",
                        Toast.LENGTH_SHORT).show();
            });

            FirebaseUser user = auth.getCurrentUser();
            database.getReference("users").child(user.getUid()).child("createdRequests")
                    .setValue(rideRequest);

            Intent intent = new Intent( NewRideActivity.this, ReviewRequestsActivity.class );
            startActivity( intent );
        });
    }
}