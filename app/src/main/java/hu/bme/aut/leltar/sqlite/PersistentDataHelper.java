package hu.bme.aut.leltar.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import hu.bme.aut.leltar.data.Device;

public class PersistentDataHelper {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    private String[] deviceColumns = {
            DevicesTable.Columns._id.name(),
            DevicesTable.Columns.maker.name(),
            DevicesTable.Columns.type.name(),
            DevicesTable.Columns.basic_name.name(),
            DevicesTable.Columns.details.name(),
            DevicesTable.Columns.value.name()
    };

    private String[] rentColumns = {
            RentsTable.Columns._id.name(),
            RentsTable.Columns.out.name(),
            RentsTable.Columns.back.name(),
            RentsTable.Columns.out_date.name(),
            RentsTable.Columns.prop_back_date.name(),
            RentsTable.Columns.act_back_date.name(),
            RentsTable.Columns.given_by.name(),
            RentsTable.Columns.given_to.name()
    };

    private String[] devicesOfRentColumns = {
            DevicesOfRent.Columns.rent_id.name(),
            DevicesOfRent.Columns.device_id.name()
    };

    public PersistentDataHelper(final Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLiteException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void persistDevice(Device device) {
        final ContentValues values = new ContentValues();
        values.put(DevicesTable.Columns.maker.name(), device.getMaker());
        values.put(DevicesTable.Columns.type.name(), device.getType());
        values.put(DevicesTable.Columns.basic_name.name(), device.getBasicName());
        values.put(DevicesTable.Columns.details.name(), device.getDetails());
        values.put(DevicesTable.Columns.value.name(), device.getValue());

        db.insert(DevicesTable.TABLE_DEVICES, null, values);
    }
}
