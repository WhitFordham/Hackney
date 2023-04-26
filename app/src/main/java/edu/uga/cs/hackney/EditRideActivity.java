package edu.uga.cs.hackney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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


    }
}