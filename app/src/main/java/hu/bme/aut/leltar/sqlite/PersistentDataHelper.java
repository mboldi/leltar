package hu.bme.aut.leltar.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.leltar.data.Device;
import hu.bme.aut.leltar.data.Rent;

import static java.lang.System.in;

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
            final Device device = cursorToDevice(cursor);
            devices.add(device);
            cursor.moveToNext();
        }

        return devices;
    }

    private Device cursorToDevice(Cursor cursor) {
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

    public void persistRent(Rent rent) {
        final ContentValues values = new ContentValues();
        values.put(RentsTable.Columns.given_to.name(), rent.getGivenTo());
        values.put(RentsTable.Columns.given_by.name(), rent.getGivenBy());
        values.put(RentsTable.Columns.out_date.name(), rent.getOutDate());
        values.put(RentsTable.Columns.prop_back_date.name(), rent.getPropBackDate());
        values.put(RentsTable.Columns.out.name(), true);

        long rentId = db.insert(RentsTable.TABLE_RENTS, null, values);

        for (Device device : rent.getDevices()) {
            final ContentValues linkValues = new ContentValues();
            linkValues.put(DevicesOfRent.Columns.rent_id.name(), rentId);
            linkValues.put(DevicesOfRent.Columns.device_id.name(), device.get_id());

            db.insert(DevicesOfRent.TABLE_DEVICES_OF_RENTS, null, linkValues);
        }
    }

    public List<Rent> restoreRents() {
        final List<Device> devices = restoreDevices();

        final List<Rent> rents = new ArrayList<>();
        final Cursor cursor = db.query(RentsTable.TABLE_RENTS, rentColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Rent rent = cursorToRent(cursor);

            for (Device device : devices) {
                if(devicesOfRent(rent.get_id()).contains(device.get_id()))
                    rent.addDevice(device);
            }

            rents.add(rent);
            cursor.moveToNext();
        }

        return rents;
    }

    private Rent cursorToRent(Cursor cursor) {
        final Rent rent = new Rent();
        rent.set_id(cursor.getInt(RentsTable.Columns._id.ordinal()));
        rent.setOutDate(cursor.getString(RentsTable.Columns.out_date.ordinal()));
        rent.setPropBackDate(cursor.getString(RentsTable.Columns.prop_back_date.ordinal()));
        rent.setActBackDate(cursor.getString(RentsTable.Columns.act_back_date.ordinal()));
        rent.setGivenBy(cursor.getString(RentsTable.Columns.given_by.ordinal()));
        rent.setGivenTo(cursor.getString(RentsTable.Columns.given_to.ordinal()));
        rent.setOut(cursor.getInt(RentsTable.Columns.out.ordinal()) == 1);

        return rent;
    }

    private List<Integer> devicesOfRent(int rentId) {
        List<Integer> deviceIds = new ArrayList<>();
        final Cursor cursor = db.rawQuery("select * from " + DevicesOfRent.TABLE_DEVICES_OF_RENTS + " where " + DevicesOfRent.Columns.rent_id.name() + " = " + rentId, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            deviceIds.add(cursor.getInt(DevicesOfRent.Columns.device_id.ordinal()));

            cursor.moveToNext();
        }

        return deviceIds;
    }

    public void updateRent(Rent rent) {
        final ContentValues values = new ContentValues();
        values.put(RentsTable.Columns.given_to.name(), rent.getGivenTo());
        values.put(RentsTable.Columns.given_by.name(), rent.getGivenBy());
        values.put(RentsTable.Columns.out_date.name(), rent.getOutDate());
        values.put(RentsTable.Columns.prop_back_date.name(), rent.getPropBackDate());
        values.put(RentsTable.Columns.act_back_date.name(), rent.getActBackDate());
        values.put(RentsTable.Columns.out.name(), rent.isOut());


        db.update(RentsTable.TABLE_RENTS, values, "_id=" + rent.get_id(), null);
    }
}
