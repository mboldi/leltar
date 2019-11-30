package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import hu.bme.aut.leltar.adapter.DeviceAdapter;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class DevicesListActivity extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerView.Adapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PersistentDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //String _id = getIntent().getStringExtra("alma");
        //Log.d("TEST", _id);

        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();

        setContentView(R.layout.activity_devices_list);

        list = findViewById(R.id.devicesList);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        deviceAdapter = new DeviceAdapter(dataHelper);
        list.setAdapter(deviceAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataHelper.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.close();
    }
}
