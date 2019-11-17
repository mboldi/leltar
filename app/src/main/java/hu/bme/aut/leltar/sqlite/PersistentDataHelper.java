package hu.bme.aut.leltar.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

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

    public List<Device> restoreDevices() {
        final List<Device> devices = new ArrayList<>();
        final Cursor cursor = db.query(DevicesTable.TABLE_DEVICES, deviceColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Device device = cursonToDevice(cursor);
            devices.add(device);
            cursor.moveToNext();
        }

        return devices;
    }

    private Device cursonToDevice(Cursor cursor) {
        final Device device = new Device();
        device.set_id(cursor.getInt(DevicesTable.Columns._id.ordinal()));
        device.setMaker(cursor.getString(DevicesTable.Columns.maker.ordinal()));
        device.setType(cursor.getString(DevicesTable.Columns.type.ordinal()));
        device.setBasicName(cursor.getString(DevicesTable.Columns.basic_name.ordinal()));
        device.setDetails(cursor.getString(DevicesTable.Columns.details.ordinal()));
        device.setValue(cursor.getInt(DevicesTable.Columns.value.ordinal()));
        device.setExpanded(false);

        return device;
    }

    public void removeDevice(Device device) {
        db.delete(DevicesTable.TABLE_DEVICES, DevicesTable.Columns._id.name() + "=" + device.get_id(), null);
    }
}
