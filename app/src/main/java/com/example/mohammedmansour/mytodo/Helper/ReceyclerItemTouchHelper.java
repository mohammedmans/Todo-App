package com.example.mohammedmansour.mytodo.Helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.mohammedmansour.mytodo.Adapters.TodoListAdapter;

public class ReceyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private ReceyclerItemTouchHelperListener listener;
    public ReceyclerItemTouchHelper(int dragDirs, int swipeDirs,ReceyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener != null){
            listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder) {
        View foregroundView = ((TodoListAdapter.CustomViewHolder)viewHolder).parent;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        View foregroundView = ((TodoListAdapter.CustomViewHolder)viewHolder).parent;
        getDefaultUIUtil().onDraw(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((TodoListAdapter.CustomViewHolder)viewHolder).parent;
        getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(@Nullable ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            View foregroundView = ((TodoListAdapter.CustomViewHolder)viewHolder).parent;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}