package com.thebaileybrew.retailinventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.thebaileybrew.retailinventory.MainActivity;

import java.nio.charset.IllegalCharsetNameException;

public class InventoryProvider extends ContentProvider {
    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    private InventoryDbHelper mDbHelper;
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(InventoryContract.InventoryEntry.CONTENT_AUTHORITY, InventoryContract.InventoryEntry.PATH_INVENTORY, 100);
        sUriMatcher.addURI(InventoryContract.InventoryEntry.CONTENT_AUTHORITY, InventoryContract.InventoryEntry.PATH_INVENTORY + "/#", 101);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch(match) {
            case INVENTORY:
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection,
                        selection, selectionArgs,null,null,sortOrder);
                break;
            case INVENTORY_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(InventoryContract.InventoryEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return InventoryContract.InventoryEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return InventoryContract.InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return insertInventory(uri, values);
            case INVENTORY_ID:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
        return null;
    }
    private Uri insertInventory(Uri uri, ContentValues values) {
        //Check for null item name
        String itemName = values.getAsString(InventoryContract.InventoryEntry.PRODUCT_NAME);
        //if (TextUtils.isEmpty(itemName)) {
        //    throw new IllegalArgumentException("Item must have a name");
        //}
        //Check for zero quantity
        int itemQty = values.getAsInteger(InventoryContract.InventoryEntry.PRODUCT_QTY);
        //if (itemQty == 0) {
        //    throw new IllegalArgumentException("Item must have a quantity");
        //}
        //Checks for valid price
        String itemPrice = values.getAsString(InventoryContract.InventoryEntry.PRODUCT_PRICE);
        //if(TextUtils.isEmpty(itemPrice)) {
        //    throw new IllegalArgumentException("Item must have a price");
        //}

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(InventoryContract.InventoryEntry.TABLE_NAME, null, values);

        //Once the ID is known, return the new URI
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return database.delete(InventoryContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
            case INVENTORY_ID:
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return database.delete(InventoryContract.InventoryEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                return updateInventory(uri, values, selection, selectionArgs);
            case INVENTORY_ID:
                //For the INVENTORY_ID code, extract the ID from the URI
                selection = InventoryContract.InventoryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri)) };
                return updateInventory(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not support for " + uri);
        }
    }
    private int updateInventory(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: Update the selection
        if (values.containsKey(InventoryContract.InventoryEntry.PRODUCT_NAME)) {
            String name = values.getAsString(InventoryContract.InventoryEntry.PRODUCT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Item requires a Description");
            }
        }

        if (values.containsKey(InventoryContract.InventoryEntry.PRODUCT_SYSTEM)) {
            int category = values.getAsInteger(InventoryContract.InventoryEntry.PRODUCT_SYSTEM);
            if (!InventoryContract.InventoryEntry.isValidSystem(category)) {
                throw new IllegalArgumentException("Item requires a valid System");
            }
        }


        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        return database.update(InventoryContract.InventoryEntry.TABLE_NAME, values, selection, selectionArgs);
    }
}
