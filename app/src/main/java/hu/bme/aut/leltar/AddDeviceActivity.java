package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.bme.aut.leltar.data.Device;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class AddDeviceActivity extends AppCompatActivity {
    EditText etMaker, etType, etBasicName, etDetails, etValue;
    Button btnSave;

    PersistentDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();

        etMaker = findViewById(R.id.etMaker);
        etType = findViewById(R.id.etType);
        etBasicName = findViewById(R.id.etBasicName);
        etDetails = findViewById(R.id.etDetails);
        etValue = findViewById(R.id.etValue);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device device = new Device();
                device.setMaker(etMaker.getText().toString());
                device.setType(etType.getText().toString());
                device.setBasicName(etBasicName.getText().toString());
                device.setDetails(etDetails.getText().toString());
                device.setValue(Integer.parseInt(etValue.getText().toString()));

                dataHelper.persistDevice(device);

                Intent viewListIntent = new Intent();
                viewListIntent.setClass(AddDeviceActivity.this, DevicesListActivity.class);
                startActivity(viewListIntent);
            }
        });
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
