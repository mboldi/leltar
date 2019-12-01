package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;
import hu.bme.aut.leltar.data.Rent;

public class ViewRentDeviceAdapter extends RecyclerView.Adapter<ViewRentDeviceAdapter.DeviceInRentViewHolder> {

    private Rent rent;
    private List<Device> devices;
    private Map<Device, Boolean> isDeviceBack;

    static class DeviceInRentViewHolder extends RecyclerView.ViewHolder {

        TextView deviceNameTextView;
        CheckBox isBackCheckbox;

        public DeviceInRentViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceNameTextView = itemView.findViewById(R.id.tvDeviceNameRentDeviceElement);
            isBackCheckbox = itemView.findViewById(R.id.cbIsBack);
        }
    }

    public ViewRentDeviceAdapter(Rent rent) {
        this.rent = rent;
        this.devices = rent.getDevices();
        isDeviceBack = new HashMap<>();

        for (Device device : devices) {
            isDeviceBack.put(device, false);
        }
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

        holder.isBackCheckbox.setVisibility(rent.isOut() ? View.VISIBLE : View.INVISIBLE);

        holder.isBackCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDeviceBack.put(device, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public boolean isAllBack() {
        for (Device device : devices) {
            if (!isDeviceBack.get(device))
                return false;
        }

        return true;
    }

}
