package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class NewRideActivity extends AppCompatActivity {

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
    }
}