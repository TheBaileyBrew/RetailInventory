package com.thebaileybrew.retailinventory.QueryPull;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class GameLoader extends AsyncTaskLoader<List<Game>>{

    private static final String LOG_TAG = GameLoader.class.getName();

    private String mUrl;

    public GameLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.v("Starting Loader", "Yes");
        forceLoad();
    }

    @Override
    public List<Game> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.v("Load in Background", "Yes");
        return super.onLoadInBackground();
    }
}
