package com.thebaileybrew.retailinventory.customclasses;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.thebaileybrew.retailinventory.EditorActivity;
import com.thebaileybrew.retailinventory.QueryPull.Game;
import com.thebaileybrew.retailinventory.R;
import com.thebaileybrew.retailinventory.data.InventoryContract;

import static com.thebaileybrew.retailinventory.data.InventoryProvider.*;

import java.util.List;

public class AutoCompleteArrayAdapter extends ArrayAdapter<Game> {
    final static String LOG_TAG = AutoCompleteArrayAdapter.class.getSimpleName();

    Context mContext;
    int mLayoutResource;
    CursorAdapter mCursor;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     *
     */
    public AutoCompleteArrayAdapter(@NonNull Context context, int resource, Cursor cursor) {
        super(context, resource);
        this.mContext = context;
        this.mLayoutResource = resource;
        this.mCursor = new CursorAdapter(mContext, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                View currentItemView = LayoutInflater.from(context).inflate(
                        R.layout.support_simple_spinner_dropdown_item, parent, false);
                ViewHolder vholder = new ViewHolder(currentItemView);
                currentItemView.setTag("holder");

                return currentItemView;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                final ViewHolder holder = (ViewHolder) view.getTag();

                String gameName = cursor.getString(cursor.getColumnIndex(InventoryContract.GameEntry.GAME_NAME));
                holder.textViewDisplay.setText(gameName);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDisplay;

        public ViewHolder (View itemView) {
            super(itemView);
            textViewDisplay = itemView.findViewById(R.id.textViewItem);
        }
    }

}
