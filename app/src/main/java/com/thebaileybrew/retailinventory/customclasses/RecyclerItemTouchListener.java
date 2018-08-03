package com.thebaileybrew.retailinventory.customclasses;

import android.support.v7.widget.RecyclerView;

public interface RecyclerItemTouchListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
