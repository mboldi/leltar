package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button listButton, showAddDeviceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listButton = findViewById(R.id.btShowList);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewListIntent = new Intent();
                viewListIntent.setClass(MainActivity.this, DevicesListActivity.class);
                startActivity(viewListIntent);
            }
        });

        showAddDeviceButton = findViewById(R.id.btShowAddDevice);
        showAddDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewListIntent = new Intent();
                viewListIntent.setClass(MainActivity.this, AddDeviceActivity.class);
                startActivity(viewListIntent);
            }
        });
    }
}
