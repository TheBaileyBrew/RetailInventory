package com.thebaileybrew.retailinventory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thebaileybrew.retailinventory.data.InventoryContract;
import com.thebaileybrew.retailinventory.data.InventoryDbHelper;

import static com.thebaileybrew.retailinventory.data.InventoryContract.*;

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = EditorActivity.class.getSimpleName();

    private EditText mProductName;
    private Spinner mProductCategory;
    private EditText mProductPrice;
    private TextView mProductQuantity;
    private Spinner mProductSupplier;
    private TextView mProductSupplierPhone;
    private TextView mProductSupplierAddress;
    private Button increaseQuantity;
    private Button decreaseQuantity;

    private int mCategory = InventoryEntry.CATEGORY_GAME;
    private int mSupplier = InventoryEntry.SUPPLIER_SONY;
    private double mPrice = 0;
    private int mQuantity = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        increaseQuantity = findViewById(R.id.increase_qty);
        decreaseQuantity = findViewById(R.id.decrease_qty);
        mProductName = findViewById(R.id.product_name_edit_text); //ET - String
        mProductCategory = findViewById(R.id.product_category_spinner); //Spinner - variable direct
        mProductPrice = findViewById(R.id.product_price_edit_text); //ET - String
        mProductQuantity = findViewById(R.id.product_quantity_update); //TV - String
        mProductSupplier = findViewById(R.id.product_supplier_name); //Spinner - variable direct
        mProductSupplierPhone = findViewById(R.id.product_supplier_phone); //TV - String
        mProductSupplierAddress = findViewById(R.id.product_supplier_address); //TV - String

        setupSpinner();
        increaseQty();
        decreaseQty();
    }

    private void decreaseQty() {
        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProductQuantity.getText().toString().equals("0")) {
                    Toast.makeText(EditorActivity.this,
                            "Cannont Have Negative Recieved", Toast.LENGTH_SHORT).show();
                } else {
                    mQuantity = mQuantity -1;
                    mProductQuantity.setText(String.valueOf(mQuantity));
                }
            }
        });
    }

    private void increaseQty() {
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProductQuantity.getText().toString().equals("9")) {
                    Toast.makeText(EditorActivity.this,
                            "Max Quantity Recieved", Toast.LENGTH_SHORT).show();
                } else {
                    mQuantity = mQuantity + 1;
                    mProductQuantity.setText(String.valueOf(mQuantity));
                }
            }
        });

    }

    private void setupSpinner() {
        ArrayAdapter categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_category_types, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mProductCategory.setAdapter(categoryAdapter);

        ArrayAdapter supplierAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_supplier_types, android.R.layout.simple_spinner_item);
        supplierAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mProductSupplier.setAdapter(supplierAdapter);

        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedItem)) {
                    if(selectedItem.equals(getString(R.string.games))) {
                        mCategory = InventoryEntry.CATEGORY_GAME;
                    } else if (selectedItem.equals(getString(R.string.hardware))) {
                        mCategory = InventoryEntry.CATEGORY_HARDWARE;
                    } else if (selectedItem.equals(getString(R.string.accessories))) {
                        mCategory = InventoryEntry.CATEGORY_ACCESSORY;
                    } else {
                        mCategory = InventoryEntry.CATEGORY_POWER;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mProductSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedItem)) {
                    if (selectedItem.equals(getString(R.string.sony))) {
                        mSupplier = InventoryEntry.SUPPLIER_SONY;
                        mProductSupplierPhone.setText(getString(R.string.sony_phone));
                        mProductSupplierAddress.setText(getString(R.string.sony_address));
                    } else if (selectedItem.equals(getString(R.string.microsoft))) {
                        mSupplier = InventoryEntry.SUPPLIER_MICROSOFT;
                        mProductSupplierPhone.setText(getString(R.string.microsoft_phone));
                        mProductSupplierAddress.setText(getString(R.string.microsoft_address));
                    } else {
                        mSupplier = InventoryEntry.SUPPLIER_NINTENDO;
                        mProductSupplierPhone.setText(getString(R.string.nintendo_phone));
                        mProductSupplierAddress.setText(getString(R.string.nintendo_address));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void insertInventory() {
        //Read from input
        String productName = mProductName.getText().toString().toUpperCase().trim();
        String productPrice = mProductPrice.getText().toString().trim();
        String productSupplierAddress = mProductSupplierAddress.getText().toString();
        String productSupplierPhone = mProductSupplierPhone.getText().toString();
        String productQuantity = mProductQuantity.getText().toString();

        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.PRODUCT_NAME, productName);
        values.put(InventoryEntry.PRODUCT_PRICE, productPrice);
        values.put(InventoryEntry.PRODUCT_QTY, productQuantity);
        values.put(InventoryEntry.PRODUCT_CATEGORY, mCategory);
        values.put(InventoryEntry.PRODUCT_SUPPLIER, mSupplier);
        values.put(InventoryEntry.PRODUCT_SUPPLIER_PHONE, productSupplierPhone);
        values.put(InventoryEntry.PRODUCT_SUPPLIER_ADDRESS, productSupplierAddress);

        //Insert a new row for the equipment into the database, and return the ID of that row
        long newRowId = db.insert(InventoryEntry.TABLE_NAME,null, values);

        //Show toast depending on the success of insertion into database table
        if(newRowId == -1) {
            Toast.makeText(this, "Error saving item", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

    }

    //Menu specific options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu for the editor
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertInventory();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
