package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hu.bme.aut.leltar.adapter.RentListDeviceAdapter;
import hu.bme.aut.leltar.data.Device;
import hu.bme.aut.leltar.data.Rent;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class AddRentActivity extends AppCompatActivity {

    private Button chooseOutDateButton, chooseBackDateButton, addDeviceButton, saveRentButton;
    private TextView outDateTextbox, backDateTextbox;
    private EditText renterNameEdittext, giverNameEdittext;

    private RecyclerView deviceList;
    private RentListDeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PersistentDataHelper dataHelper;

    private List<Device> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rent);

        outDateTextbox = findViewById(R.id.tvOutDate);
        backDateTextbox = findViewById(R.id.tvPropBackDate);

        chooseOutDateButton = findViewById(R.id.btChooseOutDate);
        chooseBackDateButton = findViewById(R.id.btChooseBackDate);
        addDeviceButton = findViewById(R.id.btAddDevices);
        saveRentButton = findViewById(R.id.btnSaveRent);

        renterNameEdittext = findViewById(R.id.etRenter);
        giverNameEdittext = findViewById(R.id.etGivenBy);

        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();
        devices = dataHelper.restoreDevices();

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

        addDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceAdderPopupController popupController = new DeviceAdderPopupController();
                popupController.showPopupWindow(v, devices, deviceAdapter);
            }
        });

        saveRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(necessaryNotEmpty()) {
                    Rent rent = new Rent();
                    rent.setGivenTo(renterNameEdittext.getText().toString());
                    rent.setGivenBy(giverNameEdittext.getText().toString());
                    rent.setOutDate(outDateTextbox.getText().toString());
                    rent.setPropBackDate(backDateTextbox.getText().toString());
                    rent.setDevices(deviceAdapter.getDevices());

                    dataHelper.persistRent(rent);

                    Intent viewMain = new Intent();
                    viewMain.setClass(AddRentActivity.this, MainActivity.class);
                    startActivity(viewMain);
                }
                else if(deviceAdapter.getItemCount() == 0) {
                    Toast.makeText(v.getContext(), "Adj hozzá a rendeléshez eszközöket!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "Add meg a bérbevevő és -adó nevét!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deviceList = findViewById(R.id.devicesInRent);
        deviceList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        deviceList.setLayoutManager(layoutManager);

        deviceAdapter = new RentListDeviceAdapter(devices);
        deviceList.setAdapter(deviceAdapter);
    }

    public boolean necessaryNotEmpty() {
        return !(renterNameEdittext.getText().toString().isEmpty() ||
                giverNameEdittext.getText().toString().isEmpty() ||
                deviceAdapter.getItemCount() == 0);
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
