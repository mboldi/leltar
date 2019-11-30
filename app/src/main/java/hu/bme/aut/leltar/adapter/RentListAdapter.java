package hu.bme.aut.leltar.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Rent;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class RentListAdapter extends RecyclerView.Adapter<RentListAdapter.RentListViewholder> {

    PersistentDataHelper dataHelper;
    List<Rent> rents;

    static class RentListViewholder extends RecyclerView.ViewHolder {
        TextView givenToTextview, intervalTextview, amountTextview;
        Button openRentButton;

        View itemView;

        public RentListViewholder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;

            givenToTextview = itemView.findViewById(R.id.tvGivenToRentlist);
            intervalTextview = itemView.findViewById(R.id.tvIntervalRentlist);
            amountTextview = itemView.findViewById(R.id.tvAmountOfStuff);

            openRentButton = itemView.findViewById(R.id.btnOpen);

            //itemView.setBackgroundColor(Color.rgb(255, 213, 79));   //yellow
            //itemView.setBackgroundColor(Color.rgb(76, 175, 80));    //green
        }
    }

    public RentListAdapter(PersistentDataHelper dataHelper) {
        this.dataHelper = dataHelper;
        rents = dataHelper.restoreRents();
    }

    @NonNull
    @Override
    public RentListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rentDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_list_element, parent, false);
        return new RentListViewholder(rentDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RentListViewholder holder, int position) {
        final Rent rent = rents.get(position);
        holder.amountTextview.setText(Integer.toString(rent.getDevices().size()) + " db eszköz");
        holder.intervalTextview.setText(rent.getOutDate() + " - " +
                (rent.isOut() ? (rent.getPropBackDate() + "(?)") : rent.getActBackDate()));
        holder.givenToTextview.setText(rent.getGivenTo());

        holder.itemView.setBackgroundColor(rent.isOut()? Color.rgb(255, 213, 79) : Color.rgb(76, 175, 80));

    }

    @Override
    public int getItemCount() {
        return rents.size();
    }

}
