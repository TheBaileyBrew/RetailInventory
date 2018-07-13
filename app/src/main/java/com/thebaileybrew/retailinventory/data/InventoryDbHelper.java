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
    private static final String DATABASE_NAME = "inventory.db";

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

        //String containing the SQL statement to create the mobile table in the database
        String SQL_CREATE_MOBILE_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryEntry.PRODUCT_PRICE + " REAL, "
                + InventoryEntry.PRODUCT_QTY + " INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.PRODUCT_CATEGORY + " INTEGER, "
                + InventoryEntry.PRODUCT_SUPPLIER + " INTEGER, "
                + InventoryEntry.PRODUCT_SUPPLIER_PHONE + " TEXT, "
                + InventoryEntry.PRODUCT_SUPPLIER_ADDRESS + " TEXT);";
        //Execute the SQL statement
        db.execSQL(SQL_CREATE_MOBILE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
