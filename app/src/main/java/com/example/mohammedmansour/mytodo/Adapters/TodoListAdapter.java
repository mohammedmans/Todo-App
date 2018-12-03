package com.example.mohammedmansour.mytodo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammedmansour.mytodo.Model.Todo;
import com.example.mohammedmansour.mytodo.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.CustomViewHolder>{
    List<Todo>items;
    OnItemClickListener onItemClickListener;
    Context context;
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public TodoListAdapter(List<Todo> items , Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_view,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {
        final Todo todo = items.get(i);
        customViewHolder.title.setText(todo.getTitle());
        customViewHolder.desc.setText(todo.getContent());
        customViewHolder.datatim.setText(todo.getDateTime());
        customViewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener != null){
                    onItemClickListener.OnItemClick(i,todo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return  0;
        return items.size();
    }

    public  void updateData(List<Todo>newItems){
        items = newItems;
        // want to make adapter update itself instead of create new adapter and send new data
        notifyDataSetChanged(); // refresh data on adapter

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public View parent;
        TextView title,desc,datatim;
        public CustomViewHolder(@NonNull View view) {
            super(view);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            datatim = view.findViewById(R.id.datetime);
            parent = view;
        }
    }
    public interface OnItemClickListener{
        void OnItemClick(int i,Todo todo);
    }
    public void removeTodo(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }
    public void restorTodo(Todo todo , int position){
        items.add(position,todo);
        notifyItemInserted(position);
    }
}
