package edu.uga.cs.hackney;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RideRecyclerAdapter extends RecyclerView.Adapter<RideRecyclerAdapter.RideHolder> {

    private List<Ride> rideList;
    private Context context;

    public RideRecyclerAdapter( List<Ride> rideList, Context context ) {
        this.rideList = rideList;
        this.context = context;
    }

    class RideHolder extends RecyclerView.ViewHolder {

        TextView dateAndTime;
        TextView startLocation;
        TextView endLocation;
        TextView price;

        public RideHolder(View itemView ) {
            super(itemView);

            dateAndTime = itemView.findViewById( R.id.textView2 );
            startLocation = itemView.findViewById( R.id.textView3 );
            endLocation = itemView.findViewById( R.id.textView5 );
            price = itemView.findViewById( R.id.textView6 );
        }
    }

    @NonNull
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.ride, parent, false );
        return new RideHolder( view );
    }

    @Override
    public void onBindViewHolder( RideHolder holder, int position ) {
        Ride ride = rideList.get( position );

        String key = ride.getKey();
        String dateAndTime = ride.getDateAndTime();
        String startLocation = ride.getStartLocation();
        String endLocation = ride.getEndLocation();
        Double price = ride.getPrice();

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
