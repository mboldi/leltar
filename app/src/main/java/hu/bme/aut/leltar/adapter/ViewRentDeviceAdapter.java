package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;

public class ViewRentDeviceAdapter extends RecyclerView.Adapter<ViewRentDeviceAdapter.DeviceInRentViewHolder> {

    private List<Device> devices;

    static class DeviceInRentViewHolder extends RecyclerView.ViewHolder {

        TextView deviceNameTextView;

        public DeviceInRentViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceNameTextView = itemView.findViewById(R.id.tvDeviceNameRentDeviceElement);
        }
    }

    public ViewRentDeviceAdapter(List<Device> devices) {
        this.devices = devices;
    }

    @NonNull
    @Override
    public DeviceInRentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rent_device_element, parent, false);
        return new DeviceInRentViewHolder(deviceDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceInRentViewHolder holder, int position) {
        final Device device = devices.get(position);
        holder.deviceNameTextView.setText(device.getMaker() + " " + device.getType());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
