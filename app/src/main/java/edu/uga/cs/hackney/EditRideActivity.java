package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditRideActivity extends AppCompatActivity {
    EditText editDate;
    EditText editTime;
    EditText editStart;
    EditText editEnd;
    EditText editPrice;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ride);

        editDate = findViewById(R.id.editTextDate);
        editTime = findViewById(R.id.editTextTime);
        editStart = findViewById(R.id.editTextTextPostalAddress);
        editEnd = findViewById(R.id.editTextTextPostalAddress2);
        editPrice = findViewById(R.id.editTextNumberDecimal);
        save = findViewById(R.id.button11);

        String rideKey = getIntent().getStringExtra("key");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(user.getUid()).
                child("createdRides").child(rideKey);
    }
}