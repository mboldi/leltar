package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> devices = new ArrayList<Device>();

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDeviceMaker, tvDeviceQuantity, tvDeviceBasicName, tvDeviceDetails, tvDeviceType;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDeviceBasicName = itemView.findViewById(R.id.tvDeviceBasicName);
            tvDeviceMaker = itemView.findViewById(R.id.tvDeviceMaker);
            tvDeviceDetails = itemView.findViewById(R.id.tvDeviceDetails);
            tvDeviceType = itemView.findViewById(R.id.tvDeviceType);
            tvDeviceQuantity = itemView.findViewById(R.id.tvDeviceQuantity);
        }
    }

    public DeviceAdapter() {
        for (int i = 0; i < 25; i++) {
            Device tmp = new Device("Sony", "valami", "Device" + i, i * 10000);
            tmp.setDetails("Tulajdonsagok");
            devices.add(tmp);
        }
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_element, parent, false);
        return new DeviceViewHolder(deviceDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = devices.get(position);
        holder.tvDeviceQuantity.setText("10");
        holder.tvDeviceType.setText(device.getType());
        holder.tvDeviceDetails.setText(device.getDetails());
        holder.tvDeviceMaker.setText(device.getMaker());
        holder.tvDeviceBasicName.setText(device.getBasicName());
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
