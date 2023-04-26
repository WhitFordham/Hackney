package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RideManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_management);

        Button newRideButton = findViewById(R.id.button6);
        Button rideRequestButton = findViewById(R.id.button7);
        Button rideOffersButton = findViewById(R.id.button8);
        Button pointsButton = findViewById(R.id.button9);

        newRideButton.setOnClickListener(view -> {
            Intent intent = new Intent( RideManagementActivity.this, NewRideActivity.class );
            startActivity( intent );
        });

        rideRequestButton.setOnClickListener(view -> {
            Intent intent = new Intent( RideManagementActivity.this, ReviewRequestsActivity.class );
            startActivity( intent );
        });

        rideOffersButton.setOnClickListener(view -> {
            Intent intent = new Intent( RideManagementActivity.this, ReviewOffersActivity.class );
            startActivity( intent );
        });

        pointsButton.setOnClickListener(view -> {
            Intent intent = new Intent( RideManagementActivity.this, PointsActivity.class );
            startActivity( intent );
        });
    }
}