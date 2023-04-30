package edu.uga.cs.hackney;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editDate.setText(dataSnapshot.child("date").getValue().toString());
                editTime.setText(dataSnapshot.child("time").getValue().toString());
                editStart.setText(dataSnapshot.child("startLocation").getValue().toString());
                editEnd.setText(dataSnapshot.child("endLocation").getValue().toString());
                editPrice.setText(dataSnapshot.child("price").getValue().toString());

                String date = editDate.getText().toString();
                String time = editTime.getText().toString();
                String startLocation = editStart.getText().toString();
                String endLocation = editEnd.getText().toString();
                Double price = Double.valueOf(editPrice.getText().toString());
                final Ride ride = new Ride(date, time, startLocation, endLocation, price);

                save.setOnClickListener(view -> {
                    dataSnapshot.getRef().setValue(ride);
                    Intent intent = new Intent(EditRideActivity.this, UserRidesActivity.class);
                    startActivity(intent);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}