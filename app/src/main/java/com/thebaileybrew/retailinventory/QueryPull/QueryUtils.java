package com.thebaileybrew.retailinventory.QueryPull;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.thebaileybrew.retailinventory.data.InventoryContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    //Tag for LOG
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    //Blank constructor class
    private QueryUtils() {}

    public static List<Game> extractDataFromJson(Context context) {
        String jsonResponse = "";
        try {
            jsonResponse = readFromStream(context);
        } catch (IOException ie) {
            ie.printStackTrace();
        }

        List<Game> gameData = parseJsonData(jsonResponse);
        return gameData;
    }

    private static List<Game> parseJsonData(String jsonResponse) {
        String gameName;
        String gameRelease;
        String gameDeveloper;
        String gameGenre;
        String gameSystem;
        //Check for null JSON
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        List<Game> gameData = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            gameName = baseJsonObject.getString("game");
            JSONArray baseJsonArraySystem = baseJsonObject.getJSONArray("details");
            for (int s = 0; s < baseJsonArraySystem.length(); s++) {
                JSONObject baseJsonDetailsObject = baseJsonArraySystem.getJSONObject(s);
                JSONArray baseJsonDetailsObjectArray = baseJsonDetailsObject.getJSONArray("details");
                JSONObject gameSpecificDetails = baseJsonDetailsObjectArray.getJSONObject(0);
                gameGenre = gameSpecificDetails.getString("GENRE");
                gameDeveloper = gameSpecificDetails.getString("DEVELOPER");
                gameSystem = gameSpecificDetails.getString("SYSTEM");
                gameRelease = gameSpecificDetails.getString("RELEASE DATE");
                gameData.add(new Game(gameName,gameDeveloper,gameRelease,gameGenre,gameSystem));
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return gameData;
    }

    private static String readFromStream(Context context) throws IOException {
        StringBuilder output = new StringBuilder();
        //Get contextual JSON data
        AssetManager assetManager = context.getAssets();
        //Read JSON data
        InputStream in = assetManager.open("gamedata.json");
        InputStreamReader inputStreamReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while (line != null) {
            output.append(line);
            line = reader.readLine();
        }
        in.close();
        return output.toString();
    }
}

