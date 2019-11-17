package hu.bme.aut.leltar.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DevicesTable {
    public static final String TABLE_DEVICES = "devices";

    public enum Columns {
        _id,
        maker,
        type,
        basic_name,
        details,
        value
    }

    private static final String DATABASE_CREATE = "create table " + TABLE_DEVICES
            + "(" + Columns._id.name() + " integer primary key autoincrement, "
            + Columns.maker.name() + " text not null, "
            + Columns.type.name() + " text not null, "
            + Columns.basic_name.name() + " text, "
            + Columns.details.name() + " text, "
            + Columns.value.name() + " integer);";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(DevicesTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        onCreate(database);
    }
}
