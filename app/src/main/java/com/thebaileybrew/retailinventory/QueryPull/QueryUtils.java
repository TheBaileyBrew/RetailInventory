package com.thebaileybrew.retailinventory.QueryPull;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.thebaileybrew.retailinventory.data.InventoryContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QueryUtils {
    //Tag for LOG
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    //Blank constructor class
    private QueryUtils() {}

    public static void extractDataFromJson(Context context, JSONArray workingData) {
        String gameName;
        //InventoryDbHelper mDbHelper = new InventoryDbHelper(context);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            for (int g = 0; g < workingData.length(); g++) {
                JSONObject currentGame = workingData.getJSONObject(g);
                gameName = currentGame.getString("name");
                values.put(InventoryContract.GameEntry.GAME_NAME, gameName);
                Uri newUri = context.getContentResolver().insert(InventoryContract.GameEntry.CONTENT_URI, values);
            }
        } catch (JSONException je) {
            Log.e(LOG_TAG, "JSON Parsing error ", je);
        }
    }
}
