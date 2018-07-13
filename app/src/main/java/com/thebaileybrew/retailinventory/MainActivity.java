package com.thebaileybrew.retailinventory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thebaileybrew.retailinventory.data.InventoryContract;
import com.thebaileybrew.retailinventory.data.InventoryDbHelper;

import static com.thebaileybrew.retailinventory.data.InventoryContract.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDevice = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(addDevice);
            }
        });

        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    //Temporary holder for displaying info on screen
    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.PRODUCT_NAME,
                InventoryEntry.PRODUCT_PRICE,
                InventoryEntry.PRODUCT_QTY,
                InventoryEntry.PRODUCT_CATEGORY,
                InventoryEntry.PRODUCT_SUPPLIER,
                InventoryEntry.PRODUCT_SUPPLIER_PHONE,
                InventoryEntry.PRODUCT_SUPPLIER_ADDRESS};

        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.database_details_textview);

        try {
            //Create the header and display data
            displayView.setText("Inventory contains " + cursor.getCount() + " items.\n\n");
            displayView.append(
                InventoryEntry._ID + " - " +
                InventoryEntry.PRODUCT_NAME + " - " +
                InventoryEntry.PRODUCT_PRICE + " - " +
                InventoryEntry.PRODUCT_QTY + " - " +
                InventoryEntry.PRODUCT_CATEGORY + " - " +
                InventoryEntry.PRODUCT_SUPPLIER + " - " +
                InventoryEntry.PRODUCT_SUPPLIER_PHONE + " - " +
                InventoryEntry.PRODUCT_SUPPLIER_ADDRESS);

            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int idColumnProductIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_NAME);
            int idColumnPriceIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_PRICE);
            int idColumnQuantityIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_QTY);
            int idColumnCategoryIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_CATEGORY);
            int idColumnSupplierIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_SUPPLIER);
            int idColumnSupplierPhoneIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_SUPPLIER_PHONE);
            int idColumnSupplierAddressIndex = cursor.getColumnIndex(InventoryEntry.PRODUCT_SUPPLIER_ADDRESS);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentProduct = cursor.getString(idColumnProductIndex);
                String currentPrice = cursor.getString(idColumnPriceIndex);
                int currentQuantity = cursor.getInt(idColumnQuantityIndex);
                String currentCategory = cursor.getString(idColumnCategoryIndex);
                String currentSupplier = cursor.getString(idColumnSupplierIndex);
                String currentSupplierPhone = cursor.getString(idColumnSupplierPhoneIndex);
                String currentSupplierAddress = cursor.getString(idColumnSupplierAddressIndex);

                displayView.append(
                    ("\n" + currentID + "  -  " +
                            currentProduct + "  -  " +
                            currentPrice + "  -  " +
                            currentQuantity + "  -  " +
                            currentCategory + "  -  " +
                            currentSupplier + "  -  " +
                            currentSupplierPhone + "  -  " +
                            currentSupplierAddress + "  -  "));
            }
        } finally {
            cursor.close();
        }
    }

    //Menu specific settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_view_settings:
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
