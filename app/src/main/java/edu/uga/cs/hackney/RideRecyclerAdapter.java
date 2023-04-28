package edu.uga.cs.hackney;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    final private List<Ride> rideList;
    final private Context context;
    private int layoutNumber;

    public RideRecyclerAdapter( List<Ride> rideList, Context context ) {
        this.rideList = rideList;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {

        TextView dateAndTime;
        TextView startLocation;
        TextView endLocation;
        TextView price;
        Button deleteButton;
        Button editButton;
        Button acceptButton;

        public RideHolder(View itemView ) {
            super(itemView);

            dateAndTime = itemView.findViewById( R.id.textView2 );
            startLocation = itemView.findViewById( R.id.textView3 );
            endLocation = itemView.findViewById( R.id.textView5 );
            price = itemView.findViewById( R.id.textView6 );

            if (layoutNumber == 1) {
                acceptButton = itemView.findViewById(R.id.button13);
                acceptButton.setOnClickListener(view -> {
                    Log.d("Message", "Accepted");
                });
            } else if (layoutNumber == 2) {
                editButton = itemView.findViewById(R.id.button13);
                deleteButton = itemView.findViewById(R.id.button15);

                editButton.setOnClickListener(view -> {
                    Log.d("Message", "Edit");
                });

                deleteButton.setOnClickListener(view -> {
                    Log.d("Message", "Delete");
                });
            }
        }
    }

    @NonNull
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_rides, parent, false);
        if (context instanceof ReviewOffersActivity || context instanceof ReviewRequestsActivity) {
            view = LayoutInflater.from( parent.getContext()).inflate( R.layout.ride, parent, false );
            layoutNumber = 1;
        } else if (context instanceof UserRidesActivity) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ride2, parent, false);
            layoutNumber = 2;
        }
        return new RideHolder( view );
    }

    @Override
    public void onBindViewHolder( RideHolder holder, int position ) {
        Ride ride = rideList.get( position );

        holder.dateAndTime.setText( ride.getDateAndTime());
        holder.startLocation.setText( ride.getStartLocation() );
        holder.endLocation.setText( ride.getEndLocation() );
        holder.price.setText( String.valueOf(ride.getPrice()));

    }


    @Override
    public int getItemCount() {
        return rideList.size();
    }


}
