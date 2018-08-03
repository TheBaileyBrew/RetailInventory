package com.thebaileybrew.retailinventory.data;

import android.content.Context;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thebaileybrew.retailinventory.R;
import com.thebaileybrew.retailinventory.customclasses.CustomOnClickInterface;
import com.thebaileybrew.retailinventory.customclasses.VerticalTextView;

public class InventoryCursorAdapter extends RecyclerView.Adapter<InventoryCursorAdapter.ViewHolder> {

    CursorAdapter mCursor;
    Context mContext;
    CustomOnClickInterface clickListenerInterface;

    public InventoryCursorAdapter(Context context, Cursor cursor, CustomOnClickInterface clickListenerInterface) {
        this.mContext = context;
        this.clickListenerInterface = clickListenerInterface;
        this.mCursor = new CursorAdapter(mContext, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.inventory_recycler_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                VerticalTextView systemDetail = view.findViewById(R.id.vertical_category_view);
                ConstraintLayout itemRecyclerView = view.findViewById(R.id.view_object_contraint);
                TextView itemDetail = view.findViewById(R.id.item_name_view);
                TextView quantityDetail = view.findViewById(R.id.item_qty_view);
                TextView priceDetail = view.findViewById(R.id.item_price_view);
                int systemValue = cursor.getInt(cursor.getColumnIndexOrThrow("system"));
                switch (systemValue) {
                    case 0:
                        systemDetail.setText("PS3");
                        itemRecyclerView.setBackgroundResource(R.drawable.layer_list_ps3);
                        break;
                    case 1:
                        systemDetail.setText("PS4");
                        itemRecyclerView.setBackgroundResource(R.drawable.layer_list_ps4);
                        break;
                    case 2:
                        systemDetail.setText("XBOX1");
                        itemRecyclerView.setBackgroundResource(R.drawable.layer_list_xbone);
                        break;
                    case 3:
                        systemDetail.setText("3DS");
                        itemRecyclerView.setBackgroundResource(R.drawable.layer_list_3ds);
                        break;
                    case 4:
                        systemDetail.setText("SWITCH");
                        itemRecyclerView.setBackgroundResource(R.drawable.layer_list_switch);
                    default:
                        break;
                }
                String itemValue = cursor.getString((cursor.getColumnIndexOrThrow("product")));
                itemDetail.setText(itemValue);
                String quantityValue = cursor.getString((cursor.getColumnIndexOrThrow("quantity")));
                quantityDetail.setText(quantityValue);
                String priceValue = cursor.getString((cursor.getColumnIndexOrThrow("price")));
                priceDetail.setText(priceValue);

            }
        };
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout viewForeground;
        public RelativeLayout viewBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_object_contraint);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursor.getCursor().moveToPosition(position);
        mCursor.bindView(holder.itemView, mContext, mCursor.getCursor());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mCursor.newView(mContext, mCursor.getCursor(), parent);
        final ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListenerInterface.onItemClick(view, vh.getAdapterPosition());
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListenerInterface.onLongClick(v, vh.getAdapterPosition());
                return true;
            }
        });
        return vh;
    }

}

