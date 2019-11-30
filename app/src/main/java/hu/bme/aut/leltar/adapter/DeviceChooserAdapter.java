package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;

public class DeviceChooserAdapter extends RecyclerView.Adapter<DeviceChooserAdapter.DeviceChooserViewHolder> {

    private List<Device> devices;
    private RentListDeviceAdapter rentList;

    static class DeviceChooserViewHolder extends RecyclerView.ViewHolder {

        TextView deviceName;
        Button addButton;

        public DeviceChooserViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.tvDeviceNameChooser);
            addButton = itemView.findViewById(R.id.btAddDeviceToRent);
        }
    }

    public DeviceChooserAdapter(List<Device> devices, RentListDeviceAdapter rentList) {
        this.devices = devices;
        this.rentList = rentList;
    }

    @NonNull
    @Override
    public DeviceChooserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_add_to_rent_list_element, parent, false);
        return new DeviceChooserViewHolder(deviceDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceChooserViewHolder holder, int position) {
        final Device device = devices.get(position);
        holder.deviceName.setText(device.getMaker() + " " + device.getType() + " - " + device.getBasicName());

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to deviceList
                rentList.addDevice(device);

                devices.remove(device);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
