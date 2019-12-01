package hu.bme.aut.leltar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hu.bme.aut.leltar.adapter.RentListAdapter;
import hu.bme.aut.leltar.adapter.ViewRentDeviceAdapter;
import hu.bme.aut.leltar.data.Rent;
import hu.bme.aut.leltar.sqlite.PersistentDataHelper;

public class ViewRentPopupController {

    private RecyclerView list;
    private ViewRentDeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView renterNameTextView, givenByTextView, outDateTextView, backDateTitleTextView, backDateTextView;
    private Button backButton, rentBackButton;

    PersistentDataHelper dataHelper;
    RentListAdapter adapter;

    public void showPopupWindow(final View view, final Rent rent, final PersistentDataHelper dataHelper, final RentListAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.view_rent_popup, null);

        this.dataHelper = dataHelper;
        this.adapter = adapter;

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        renterNameTextView = popupView.findViewById(R.id.tvRenterNameViewRent);
        givenByTextView = popupView.findViewById(R.id.tvGivenByViewRent);
        outDateTextView = popupView.findViewById(R.id.tvOutDateViewRent);
        backDateTitleTextView = popupView.findViewById(R.id.tvBackDateText);
        backDateTextView = popupView.findViewById(R.id.tvPropBackDateViewRent);

        backButton = popupView.findViewById(R.id.btnBackFromViewRent);
        rentBackButton = popupView.findViewById(R.id.btnGetRentBack);

        renterNameTextView.setText(rent.getGivenTo());
        givenByTextView.setText(rent.getGivenBy());
        outDateTextView.setText(rent.getOutDate());

        if (rent.isOut()) {
            backDateTextView.setText(rent.getPropBackDate());
        } else {
            backDateTitleTextView.setText(R.string.act_back_date);
            backDateTextView.setText(rent.getActBackDate());

            rentBackButton.setVisibility(View.GONE);
        }

        list = popupView.findViewById(R.id.rvDevicesViewRent);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(popupView.getContext());
        list.setLayoutManager(layoutManager);

        deviceAdapter = new ViewRentDeviceAdapter(rent);
        list.setAdapter(deviceAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        rentBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deviceAdapter.isAllBack()) {
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy. MM. dd.", Locale.GERMAN);
                    String formattedDate = df.format(c);
                    rent.setActBackDate(formattedDate);
                    rent.setOut(false);

                    dataHelper.updateRent(rent);

                    adapter.dbChanged();

                    popupWindow.dismiss();
                } else {
                    Toast.makeText(view.getContext(), "Nem jött vissza minden eszköz!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
