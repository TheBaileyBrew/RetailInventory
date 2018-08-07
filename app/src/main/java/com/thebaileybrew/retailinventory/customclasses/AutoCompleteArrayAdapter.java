package com.thebaileybrew.retailinventory.customclasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.thebaileybrew.retailinventory.EditorActivity;
import com.thebaileybrew.retailinventory.QueryPull.Game;
import com.thebaileybrew.retailinventory.R;

import java.util.List;

public class AutoCompleteArrayAdapter extends ArrayAdapter<Game> {
    final static String LOG_TAG = AutoCompleteArrayAdapter.class.getSimpleName();

    Context mContext;
    int mLayoutResource;
    List<Game> objects;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public AutoCompleteArrayAdapter(@NonNull Context context, int resource, @NonNull List<Game> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayoutResource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((EditorActivity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutResource, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItem = convertView.findViewById(R.id.textViewItem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Game gameItem = objects.get(position);
        viewHolder.textViewItem.setText(gameItem.getGameName());


        return convertView;
    }

    static class ViewHolderItem {
        TextView textViewItem;
    }
}
