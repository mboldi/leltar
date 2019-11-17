package hu.bme.aut.leltar.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DevicesOfRent {
    public static final String TABLE_DEVICES_OF_RENTS = "devices_of_rents";

    public enum Columns {
        device_id,
        rent_id
    }

    private static final String DATABASE_CREATE = "create table " + TABLE_DEVICES_OF_RENTS
            + "(" + Columns.device_id.name() + " integer not null, "
            + Columns.rent_id.name() + " integer not null);";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(RentsTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES_OF_RENTS);
        onCreate(database);
    }
}
