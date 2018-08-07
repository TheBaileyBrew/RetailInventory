package com.thebaileybrew.retailinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.thebaileybrew.retailinventory.data.InventoryContract.*;

/*
   Database helper for Inventory App. Manages the Database creation and version updates
*/
public class InventoryDbHelper extends SQLiteOpenHelper {
    public static final String TAG = InventoryDbHelper.class.getSimpleName();

    /* Name of the Database file */
    private static final String DATABASE_NAME = "gamingInventory.db";

    /* Database version. If schema is changed, the version must be incremented */
    private static final int DATABASE_VERSION = 1;

    /*
       Construct a new instance of @Link InventoryDbHelper
       @param context of the app
    */
    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
       Called when the database is first created
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        addInventoryTable(db);
        addGamesTable(db);
    }
    private void addInventoryTable(SQLiteDatabase db) {
        String SQL_CREATE_GAME_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryEntry.PRODUCT_PRICE + " REAL, "
                + InventoryEntry.PRODUCT_QTY + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.PRODUCT_SYSTEM + " INTEGER);";
        //Execute the SQL statement
        db.execSQL(SQL_CREATE_GAME_TABLE);
    }
    private void addGamesTable(SQLiteDatabase db) {
        String SQL_CREATE_ALL_GAMES = "CREATE TABLE " + InventoryContract.GameEntry.TABLE_NAME + " ("
                + InventoryContract.GameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryContract.GameEntry.GAME_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_ALL_GAMES);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            dropInventoryTable(db);
            dropGameTable(db);
        }
        onCreate(db);

    }

    private void dropGameTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + InventoryContract.GameEntry.TABLE_NAME;
        db.execSQL(sql);
    }

    private void dropInventoryTable(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS " + InventoryEntry.TABLE_NAME;
        db.execSQL(sql);
    }
}
