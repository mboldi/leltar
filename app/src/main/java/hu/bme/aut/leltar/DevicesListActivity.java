package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import hu.bme.aut.leltar.adapter.DeviceAdapter;

public class DevicesListActivity extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerView.Adapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_list);

        list = findViewById(R.id.devicesList);

        list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        deviceAdapter = new DeviceAdapter();
        list.setAdapter(deviceAdapter);
    }
}
