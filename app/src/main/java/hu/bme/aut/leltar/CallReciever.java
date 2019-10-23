package hu.bme.aut.leltar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CallReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String outNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        Toast.makeText(context, outNumber, Toast.LENGTH_LONG).show();

        this.setResultData("111");
    }
}
