package hu.bme.aut.leltar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.leltar.R;
import hu.bme.aut.leltar.data.Device;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> devices = new ArrayList<>();
    private int lastChanged = -1;
    private PersistentDataHelper dataHelper;

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView tvDeviceMaker, tvDeviceQuantity, tvDeviceBasicName, tvDeviceDetails, tvDeviceType;
        Button btEdit, btDelete;
        View subItem;

        DeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDeviceBasicName = itemView.findViewById(R.id.tvDeviceBasicName);
            tvDeviceMaker = itemView.findViewById(R.id.tvDeviceMaker);
            tvDeviceDetails = itemView.findViewById(R.id.tvDeviceDetails);
            tvDeviceType = itemView.findViewById(R.id.tvDeviceType);
            tvDeviceQuantity = itemView.findViewById(R.id.tvDeviceQuantity);
            btEdit = itemView.findViewById(R.id.btEdit);
            btDelete = itemView.findViewById(R.id.btDelete);

            subItem = itemView.findViewById(R.id.sub_item);
        }
    }

    public DeviceAdapter(PersistentDataHelper dataHelper) {
        /*for (int i = 0; i < 25; i++) {
            Device tmp = new Device("Sony", "valami", "Eszköz " + i, i * 10000);
            tmp.setDetails("Tulajdonsagok");
            devices.add(tmp);
        }*/

        devices = dataHelper.restoreDevices();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View deviceDataRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_list_element, parent, false);
        return new DeviceViewHolder(deviceDataRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, final int position) {
        final Device device = devices.get(position);
        holder.tvDeviceQuantity.setText("10");
        holder.tvDeviceType.setText(device.getType());
        holder.tvDeviceDetails.setText(device.getDetails());
        holder.tvDeviceMaker.setText(device.getMaker());
        holder.tvDeviceBasicName.setText(device.getBasicName());

        holder.subItem.setVisibility(device.isExpanded() ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastChanged != -1 && lastChanged != position){
                    devices.get(lastChanged).setExpanded(false);
                    notifyItemChanged(lastChanged);
                }

                boolean expanded = device.isExpanded();
                device.setExpanded(!expanded);

                notifyItemChanged(position);
                lastChanged = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

}
