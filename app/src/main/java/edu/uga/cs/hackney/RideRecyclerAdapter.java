package edu.uga.cs.hackney;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        TextView driver;
        TextView passenger;
        Button deleteButton;
        Button editButton;
        Button acceptButton;
        Button confirmButton;

        public RideHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.textView2);
            time = itemView.findViewById(R.id.textView11);
            startLocation = itemView.findViewById(R.id.textView3);
            endLocation = itemView.findViewById(R.id.textView5);
            price = itemView.findViewById(R.id.textView6);
            driver = itemView.findViewById(R.id.textView12);
            passenger = itemView.findViewById(R.id.textView13);

            if (layoutNumber == 1) {
                acceptButton = itemView.findViewById(R.id.button13);
                acceptButton.setOnClickListener(view -> {
                    Ride ride = rideList.get(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference userReference = database.getReference("users").child(user.getUid());

                    if (ride.getDriverID() == null) {
                        ride.setDriverID(user.getUid());
                        DatabaseReference requestReference = database.getReference("rideRequests").
                                child(ride.getKey());
                        DatabaseReference riderReference = database.getReference("users").
                                child(ride.getRiderID());

                        riderReference.child("acceptedRides").child(ride.getKey()).setValue(ride);
                        riderReference.child("createdRides").child(ride.getKey()).removeValue();
                        requestReference.removeValue();
                    }

                    if (ride.getRiderID() == null) {
                        ride.setRiderID(user.getUid());
                        DatabaseReference offerReference = database.getReference("rideOffers").
                                child(ride.getKey());
                        DatabaseReference driverReference = database.getReference("users").
                                child(ride.getDriverID());

                        driverReference.child("acceptedRides").child(ride.getKey()).setValue(ride);
                        driverReference.child("createdRides").child(ride.getKey()).removeValue();
                        offerReference.removeValue();
                    }

                    userReference.child("acceptedRides").child(ride.getKey()).setValue(ride);
                    userReference.child("createdRides").child(ride.getKey()).removeValue();

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

                    if (ride.getDriverID() != null) {
                        DatabaseReference offerReference = database.getReference("rideOffers").
                                child(ride.getKey());

                        offerReference.removeValue();
                    } else if (ride.getRiderID() != null) {
                        DatabaseReference requestReference = database.getReference("rideRequests").
                                child(ride.getKey());

                        requestReference.removeValue();
                    }
                });
            } else if (layoutNumber == 3) {
                confirmButton = itemView.findViewById(R.id.button5);
                confirmButton.setOnClickListener(view -> {
                    Ride ride = rideList.get(getAdapterPosition());
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("users");

                    ride.addPeopleConfirmed();

                    if (ride.getPeopleConfirmed() == 1) {
                        reference.child(ride.getDriverID()).child("acceptedRides").child(ride.getKey())
                                .setValue(ride);
                        reference.child(ride.getRiderID()).child("acceptedRides").child(ride.getKey())
                                .setValue(ride);

                        notifyItemRemoved(getAdapterPosition());
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("acceptedRides").child(ride.getKey()).removeValue();

                        Toast.makeText(context, "Thank you for confirming the ride. " +
                                "Please wait for the other person to confirm as well.", Toast.LENGTH_SHORT).show();
                    } else if (ride.getPeopleConfirmed() == 2) {
                        notifyItemRemoved(getAdapterPosition());
                        reference.child(ride.getDriverID()).child("points").
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Double points = Double.valueOf(snapshot.getValue().toString());
                                        reference.child(ride.getDriverID()).child("points").
                                                setValue(points + ride.getPrice());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                        reference.child(ride.getRiderID()).child("points").
                                addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Double points = Double.valueOf(snapshot.getValue().toString());
                                        reference.child(ride.getRiderID()).child("points").
                                                setValue(points - ride.getPrice());
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                        Toast.makeText(context, "Thank you for confirming the ride. " +
                                "Points have been transferred.", Toast.LENGTH_SHORT).show();

                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .child("acceptedRides").child(ride.getKey()).removeValue();
                    }
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
        } else if (context instanceof AcceptedRidesActivity) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride3, parent, false);
            layoutNumber = 3;
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

        if (layoutNumber == 3) {
            holder.driver.setText("Driver: " + ride.getDriverID());
            holder.passenger.setText("Passenger: " + ride.getRiderID());
        }

    }


    @Override
    public int getItemCount() {
        return rideList.size();
    }


}
