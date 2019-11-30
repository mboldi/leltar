package hu.bme.aut.leltar;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.leltar.adapter.DeviceChooserAdapter;
import hu.bme.aut.leltar.adapter.RentListDeviceAdapter;
import hu.bme.aut.leltar.data.Device;

public class PopupController {

    private RecyclerView list;
    private RecyclerView.Adapter deviceAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public void showPopupWindow(final View view, List<Device> devices, RentListDeviceAdapter rentList) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.choose_device_dialog_card, null);

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        boolean focusable = true;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        list = popupView.findViewById(R.id.rvDevicesList);
        list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(popupView.getContext());
        list.setLayoutManager(layoutManager);

        deviceAdapter = new DeviceChooserAdapter(devices, rentList);
        list.setAdapter(deviceAdapter);

        Button buttonEdit = popupView.findViewById(R.id.btnCancelAdding);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();

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
