package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;

public class RentListDeviceAdapter extends RecyclerView.Adapter<RentListDeviceAdapter.RentListDeviceViewHolder> {

    private List<Device> devices;

    static class RentListDeviceViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        ImageButton deleteButton;

        public RentListDeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.tvDeviceNameRentList);
            deleteButton = itemView.findViewById(R.id.ibtDeleteFromRentList);
        }
    }

    public RentListDeviceAdapter() {
        devices = new ArrayList<>();
    }

    @NonNull
    @Override
    public RentListDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_in_rent_listelement, parent, false);
        return new RentListDeviceViewHolder(deviceDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull RentListDeviceViewHolder holder, final int position) {
        final Device device = devices.get(position);

        holder.deviceName.setText(device.getMaker() + " " + device.getType());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devices.remove(device);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void addDevice(Device device) {
        devices.add(device);
        notifyDataSetChanged();
    }

    public int asd() {
        return 3;
    }
}
