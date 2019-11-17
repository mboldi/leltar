package hu.bme.aut.leltar.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RentsTable {
    public static final String TABLE_RENTS = "rents";

    public enum Columns {
        _id,
        out,
        back,
        out_date,
        prop_back_date,
        act_back_date,
        given_by,
        given_to
    }

    private static final String DATABASE_CREATE = "create table " + TABLE_RENTS
            + "(" + Columns._id.name() + " integer primary key autoincrement, "
            + Columns.out.name() + " boolean not null, "
            + Columns.back.name() + " boolean not null, "
            + Columns.out_date.name() + " text not null, "
            + Columns.prop_back_date.name() + " text not null, "
            + Columns.act_back_date.name() + " text not null, "
            + Columns.given_by.name() + " text not null, "
            + Columns.given_to.name() + " text not null);";

    public static void onCreate(final SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(final SQLiteDatabase database, final int oldVersion, final int newVersion) {
        Log.w(RentsTable.class.getName(), "Upgrading from version " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTS);
        onCreate(database);
    }
}
