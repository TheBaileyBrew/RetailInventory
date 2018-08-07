package com.thebaileybrew.retailinventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thebaileybrew.retailinventory.QueryPull.Game;
import com.thebaileybrew.retailinventory.customclasses.TextInputAutoCompleteTextView;
import com.thebaileybrew.retailinventory.data.InventoryContract;

import java.util.List;

import static com.thebaileybrew.retailinventory.data.InventoryContract.*;

public class EditorActivity extends AppCompatActivity {
    private static final String TAG = EditorActivity.class.getSimpleName();
    public ArrayAdapter<Game> productAdapter;
    String[] gameNames;
    public List<Game> GameData;
    Boolean allValidData = false;
    Boolean validName = false, validPrice = false, validQuantity = false;
    private AutoCompleteTextView mProductName;
    private TextInputLayout mProductNameLayout;
    private Spinner mProductCategory;
    private AutoCompleteTextView mProductPrice;
    private TextInputLayout mProductPriceLayout;
    private TextView mProductQuantity;
    private Button increaseQuantity;
    private Button decreaseQuantity;

    private int mSystem = InventoryEntry.SYSTEM_PS3;
    private double mPrice = 0;
    private int mQuantity = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        increaseQuantity = findViewById(R.id.increase_qty);
        decreaseQuantity = findViewById(R.id.decrease_qty);
        mProductName = findViewById(R.id.product_name_edit_text); //ET - String
        productAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item);
        mProductName.setAdapter(productAdapter);
        mProductNameLayout = findViewById(R.id.product_name_layout);
        mProductCategory = findViewById(R.id.product_category_spinner); //Spinner - variable direct
        mProductPrice = findViewById(R.id.product_price_edit_text); //ET - String
        mProductPriceLayout = findViewById(R.id.product_price_layout);
        mProductQuantity = findViewById(R.id.product_quantity_update); //TV - String

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

        mProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedItem)) {
                    if(selectedItem.equals(getString(R.string.sps3))) {
                        mSystem = InventoryEntry.SYSTEM_PS3;
                    } else if (selectedItem.equals(getString(R.string.sps4))) {
                        mSystem = InventoryEntry.SYSTEM_PS4;
                    } else if (selectedItem.equals(getString(R.string.mxbox))) {
                        mSystem = InventoryEntry.SYSTEM_XBOXONE;
                    } else if (selectedItem.equals(getString(R.string.n3ds))) {
                        mSystem = InventoryEntry.SYSTEM_N3DS;
                    } else if (selectedItem.equals(getString(R.string.nswitch))) {
                        mSystem = InventoryEntry.SYSTEM_NSWITCH;
                    } else {
                        mSystem = InventoryEntry.SYSTEM_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void insertInventory() {
        String currentItem = mProductName.getText().toString().trim();
        //Validate product name
        if(currentItem.equalsIgnoreCase("")) {
            mProductNameLayout.setError("This field cannot be blank");
        } else if (checkJSON(currentItem)) {
            mProductNameLayout.setError("This must be a valid game title");
        } else {
            validName = true;
        }

        if (mProductPrice.getText().toString().trim().equalsIgnoreCase("")) {
            mProductPriceLayout.setError("Cannot be blank");
        } else if (!mProductPrice.getText().toString().matches("\\d{2}\\.\\d{2}")) {
            mProductPriceLayout.setError("Must contain a valid price");
        } else {
            validPrice = true;
        }

        int quantity = Integer.parseInt(mProductQuantity.getText().toString());
        if (mProductQuantity.getText().toString().trim().equalsIgnoreCase("0")) {
            Toast.makeText(this, "Must have quantity greater than 1", Toast.LENGTH_SHORT).show();
        } else if (quantity < 0) {
            Toast.makeText(this, "Must not be less than 0", Toast.LENGTH_SHORT).show();
        } else {
            validQuantity = true;
        }

        if (validName && validPrice && validQuantity) {
            allValidData = true;
        }




        //Read from input
        String productName = mProductName.getText().toString().toUpperCase().trim();
        String productPrice = mProductPrice.getText().toString().trim();
        String productQuantity = mProductQuantity.getText().toString();

        if (allValidData) {
            //InventoryDbHelper mDbHelper = new InventoryDbHelper(this);
            //SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(InventoryEntry.PRODUCT_NAME, productName);
            values.put(InventoryEntry.PRODUCT_PRICE, productPrice);
            values.put(InventoryEntry.PRODUCT_QTY, productQuantity);
            values.put(InventoryEntry.PRODUCT_SYSTEM, mSystem);
            //Insert a new row for the equipment into the database, and return the ID of that row
            Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_success), Toast.LENGTH_SHORT).show();
            }
        } else {
            return;
        }

    }

    private boolean checkJSON(String currentItem) {
        String searching = "game=? ";
        String [] searchData = {currentItem};
        String[] projection = {GameEntry.GAME_NAME};
        Cursor cursor = getContentResolver().query(GameEntry.CONTENT_URI,
                projection,
                searching,
                searchData,
                null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            return false;
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
                if (allValidData) {
                    finish();
                }
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
