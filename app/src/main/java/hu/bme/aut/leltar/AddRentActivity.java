package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hu.bme.aut.leltar.adapter.RentListDeviceAdapter;
import hu.bme.aut.leltar.data.Device;

public class AddRentActivity extends AppCompatActivity {

    private Button chooseOutDateButton, chooseBackDateButton;
    private TextView outDateTextbox, backDateTextbox;
    private EditText renterNameEdittext;

    private RecyclerView deviceList;
    private RentListDeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rent);

        outDateTextbox = findViewById(R.id.tvOutDate);
        backDateTextbox = findViewById(R.id.tvPropBackDate);

        chooseOutDateButton = findViewById(R.id.btChooseOutDate);
        chooseBackDateButton = findViewById(R.id.btChooseBackDate);

        renterNameEdittext = findViewById(R.id.etRenter);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy. MM. dd.", Locale.GERMAN);
        String formattedDate = df.format(c);
        outDateTextbox.setText(formattedDate);
        backDateTextbox.setText(formattedDate);

        chooseOutDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(AddRentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String outDate = year + ". " + (month + 1) + ". " + dayOfMonth + ".";

                                outDateTextbox.setText(outDate);
                            }
                        }, year, month, dayOfMonth);

                datePicker.show();
            }
        });

        chooseBackDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(AddRentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String outDate = year + ". " + (month + 1) + ". " + dayOfMonth + ".";

                                backDateTextbox.setText(outDate);
                            }
                        }, year, month, dayOfMonth);

                datePicker.show();
            }
        });

        deviceList = findViewById(R.id.devicesInRent);
        deviceList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        deviceList.setLayoutManager(layoutManager);

        deviceAdapter = new RentListDeviceAdapter();
        deviceList.setAdapter(deviceAdapter);

        Device tmp = new Device();
        tmp.setMaker("Sony");
        tmp.setType("PMW320");

        deviceAdapter.addDevice(tmp);
    }
}
