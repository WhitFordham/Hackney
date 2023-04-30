package edu.uga.cs.hackney;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    final private List<Ride> rideList;
    final private Context context;
    private int layoutNumber;

    public RideRecyclerAdapter(List<Ride> rideList, Context context) {
        this.rideList = rideList;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        TextView startLocation;
        TextView endLocation;
        TextView price;
        Button deleteButton;
        Button editButton;
        Button acceptButton;

        public RideHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textView2);
            time = itemView.findViewById(R.id.textView11);
            startLocation = itemView.findViewById(R.id.textView3);
            endLocation = itemView.findViewById(R.id.textView5);
            price = itemView.findViewById(R.id.textView6);

            if (layoutNumber == 1) {
                acceptButton = itemView.findViewById(R.id.button13);
                acceptButton.setOnClickListener(view -> {
                    Log.d("Message", "Accepted");
                });
            } else if (layoutNumber == 2) {
                editButton = itemView.findViewById(R.id.button13);
                deleteButton = itemView.findViewById(R.id.button15);

                editButton.setOnClickListener(view -> {
                    Ride ride = rideList.get(getAdapterPosition());
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("users").child(user.getUid()).
                            child("createdRides").child(ride.getKey());

                    Intent intent = new Intent(context, EditRideActivity.class);
                    intent.putExtra("key", reference.getKey());
                    context.startActivity(intent);
                });

                deleteButton.setOnClickListener(view -> {
                    Ride ride = rideList.get(getAdapterPosition());

                    rideList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userReference = database.getReference("users").child(user.getUid()).
                            child("createdRides").child(ride.getKey());

                    userReference.removeValue().addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Ride removed", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Ride not removed", Toast.LENGTH_SHORT).show();
                    });

                    DatabaseReference offerReference = database.getReference("rideOffers").
                            child(ride.getKey());
                    offerReference.removeValue().addOnSuccessListener(aVoid -> {
                        Log.d("Yee", "Yass");
                    }).addOnFailureListener(e -> {
                        Log.d("Error", e.getMessage());
                    });

                    DatabaseReference requestReference = database.getReference("rideRequests").child(ride.getKey());
                    requestReference.removeValue();
                });
            }
        }
    }

    @NonNull
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_rides, parent, false);
        if (context instanceof ReviewOffersActivity || context instanceof ReviewRequestsActivity) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride, parent, false);
            layoutNumber = 1;
        } else if (context instanceof UserRidesActivity) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride2, parent, false);
            layoutNumber = 2;
        }
        return new RideHolder(view);
    }

    @Override
    public void onBindViewHolder(RideHolder holder, int position) {
        Ride ride = rideList.get(position);

        holder.date.setText("Date: " + ride.getDate());
        holder.time.setText("Time: " + ride.getTime());
        holder.startLocation.setText("Start Point: " + ride.getStartLocation());
        holder.endLocation.setText("Destination: " + ride.getEndLocation());
        holder.price.setText("Price: " + String.valueOf(ride.getPrice()));

    }


    @Override
    public int getItemCount() {
        return rideList.size();
    }


}
