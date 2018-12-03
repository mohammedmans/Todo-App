package com.example.mohammedmansour.mytodo.Helper;

import android.support.v7.widget.RecyclerView;

public interface ReceyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction,int position);
}
