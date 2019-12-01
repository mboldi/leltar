package hu.bme.aut.leltar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hu.bme.aut.leltar.adapter.ViewRentDeviceAdapter;
import hu.bme.aut.leltar.data.Rent;

public class ViewRentPopupController {

    private RecyclerView list;
    private ViewRentDeviceAdapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private TextView renterNameTextView, givenByTextView, outDateTextView, backDateTitleTextView, backDateTextView;
    private Button backButton, rentBackButton;

    public void showPopupWindow(final View view, Rent rent) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.view_rent_popup, null);

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

        if(rent.isOut()) {
            backDateTextView.setText(rent.getPropBackDate());
        }
        else {
            backDateTitleTextView.setText(R.string.act_back_date);
            backDateTextView.setText(rent.getActBackDate());

            rentBackButton.setVisibility(View.GONE);
        }

        list = popupView.findViewById(R.id.rvDevicesViewRent);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(popupView.getContext());
        list.setLayoutManager(layoutManager);

        deviceAdapter = new ViewRentDeviceAdapter(rent.getDevices());
        list.setAdapter(deviceAdapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
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
