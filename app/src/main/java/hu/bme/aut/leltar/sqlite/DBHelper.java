package hu.bme.aut.leltar.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "leltar.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DevicesTable.onCreate(db);
        RentsTable.onCreate(db);
        DevicesOfRent.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DevicesTable.onUpgrade(db, oldVersion, newVersion);
        RentsTable.onUpgrade(db, oldVersion, newVersion);
        DevicesOfRent.onUpgrade(db, oldVersion, newVersion);
    }
}
