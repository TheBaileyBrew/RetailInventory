package com.thebaileybrew.retailinventory;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.thebaileybrew.retailinventory.customclasses.CustomOnClickInterface;
import com.thebaileybrew.retailinventory.customclasses.RecyclerItemTouchHelpListener;
import com.thebaileybrew.retailinventory.customclasses.RecyclerItemTouchListener;
import com.thebaileybrew.retailinventory.data.InventoryContract;
import com.thebaileybrew.retailinventory.data.InventoryCursorAdapter;
import com.thebaileybrew.retailinventory.data.InventoryDbHelper;

import static com.thebaileybrew.retailinventory.data.InventoryContract.*;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    CoordinatorLayout cLayout;
    InventoryCursorAdapter inventoryCursorAdapter;
    RecyclerView recyclerView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cLayout = findViewById(R.id.coordinator_layout);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addDevice = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(addDevice);
            }
        });
            }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    //Temporary holder for displaying info on screen
    private void displayDatabaseInfo() {
        //Create and/or open a database to read from it

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.PRODUCT_NAME,
                InventoryEntry.PRODUCT_PRICE,
                InventoryEntry.PRODUCT_QTY,
                InventoryEntry.PRODUCT_SYSTEM};

        cursor = getContentResolver().query(
                InventoryEntry.CONTENT_URI, projection,
                null,
                null,
                null,
                null);

        //Find my RecyclerView Object
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerItemTouchListener helpListener;

        //Create & Set the Inventory Adapter
        inventoryCursorAdapter = new InventoryCursorAdapter(this, cursor, new CustomOnClickInterface() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MainActivity.this, "Item Clicked " + String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View v, int position) {
                Snackbar mySnackbar = Snackbar.make(cLayout,"Action To Take: ", Snackbar.LENGTH_LONG)
                        .setAction("SEE MORE GAME DETAILS...", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO: Set the view more details
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorAccent));
                mySnackbar.show();

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(inventoryCursorAdapter);
        ItemTouchHelper.SimpleCallback itemTouchCallBack = new RecyclerItemTouchHelpListener(0, ItemTouchHelper.LEFT, new RecyclerItemTouchListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
                Uri currentItemUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, position);
                getContentResolver().delete(currentItemUri,null,null);
                inventoryCursorAdapter.notifyDataSetChanged();
            }
        });
        new ItemTouchHelper(itemTouchCallBack).attachToRecyclerView(recyclerView);
        //Set the Layout Animation on load
        runLayoutAnimation(recyclerView);
    }


    private void runLayoutAnimation(RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();

        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_from_bottom);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
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
                deleteAllInventory();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllInventory() {
        getContentResolver().delete(InventoryEntry.CONTENT_URI, null, null);
        inventoryCursorAdapter.notifyDataSetChanged();
    }


}
