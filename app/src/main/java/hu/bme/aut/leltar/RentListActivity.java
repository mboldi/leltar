package hu.bme.aut.leltar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hu.bme.aut.leltar.adapter.RentListAdapter;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class RentListActivity extends AppCompatActivity {

    private RecyclerView list;
    private RecyclerView.Adapter rentAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private PersistentDataHelper dataHelper;

    private Button addRentButton;

    private ViewRentPopupController popupController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_list);

        popupController = new ViewRentPopupController();

        addRentButton = findViewById(R.id.btnRentListNewRent);

        addRentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewAddRent = new Intent();
                viewAddRent.setClass(RentListActivity.this, AddRentActivity.class);
                startActivity(viewAddRent);
            }
        });

        dataHelper = new PersistentDataHelper(this);
        dataHelper.open();

        list = findViewById(R.id.rvRentsList);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);

        rentAdapter = new RentListAdapter(dataHelper, popupController);
        list.setAdapter(rentAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataHelper.open();
    }
}
