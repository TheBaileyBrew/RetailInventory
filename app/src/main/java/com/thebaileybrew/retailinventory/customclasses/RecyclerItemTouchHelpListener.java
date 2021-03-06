package com.thebaileybrew.retailinventory.customclasses;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.thebaileybrew.retailinventory.data.InventoryCursorAdapter;

public class RecyclerItemTouchHelpListener extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchListener itemTouchHelpListener;

    public RecyclerItemTouchHelpListener(int dragDirs, int swipeDirs, RecyclerItemTouchListener listener) {
        super(dragDirs, swipeDirs);
        this.itemTouchHelpListener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((InventoryCursorAdapter.ViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    };

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((InventoryCursorAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        itemTouchHelpListener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        //Remove it from adapter & call for db.delete
    }
    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //Create the background view itself for displaying the DELETE view
        final View foregroundView = ((InventoryCursorAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView,foregroundView, dX, dY,
                actionState,isCurrentlyActive);
    }
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //Create the background view itself for displaying the DELETE view
        final View foregroundView = ((InventoryCursorAdapter.ViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDraw(c, recyclerView,foregroundView, dX,dY,
                actionState,isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
