package com.example.mohammedmansour.mytodo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mohammedmansour.mytodo.Adapters.TodoListAdapter;
import com.example.mohammedmansour.mytodo.Database.TodoDatabase;
import com.example.mohammedmansour.mytodo.Helper.ReceyclerItemTouchHelper;
import com.example.mohammedmansour.mytodo.Helper.ReceyclerItemTouchHelperListener;
import com.example.mohammedmansour.mytodo.Model.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.mohammedmansour.mytodo.AddTodo.todoItem;

public class TodoList extends AppCompatActivity implements ReceyclerItemTouchHelperListener {
    RecyclerView recyclerView;
    TodoListAdapter todoListAdapter;
    LinearLayoutManager layoutManager;
    List<Todo> AllTodos;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TodoList.this, AddTodo.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        coordinatorLayout = findViewById(R.id.coordinator);
        // but this AllTodos at first not contain Data so will crash when return item.size()
        // should return 0 but will crash because item = null so handle it on adapter
        todoListAdapter = new TodoListAdapter(AllTodos, this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(todoListAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack
                = new ReceyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack2
                = new ReceyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);

        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
        new ItemTouchHelper(itemTouchHelperCallBack2).attachToRecyclerView(recyclerView);


        todoListAdapter.setOnItemClickListener(new TodoListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int i, Todo todo) {
                Intent intent = new Intent(TodoList.this, AddTodo.class);
                intent.putExtra("update_todo","Update Todo");
                todoItem = todo;
                startActivity(intent);
//                startActivity(new Intent(TodoList.this,AddTodo.class));
            }
        });
    }

    // i want data on list update when activity on onresume
    @Override
    protected void onResume() {
        super.onResume();
        AllTodos = TodoDatabase.getDatabase(this).todoDao().getAllTodo();
        // then i want to update adapter , so create method on adapter that update

        todoListAdapter.updateData(AllTodos);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TodoListAdapter.CustomViewHolder) {
            String name = AllTodos.get(viewHolder.getAdapterPosition()).getTitle();
            final Todo deletedTodo = AllTodos.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            todoListAdapter.removeTodo(deletedIndex);
            TodoDatabase.getDatabase(this).todoDao().DeleteTodo(deletedTodo);

            Snackbar snackbar = Snackbar.make(coordinatorLayout, name, Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todoListAdapter.restorTodo(deletedTodo, deletedIndex);
                    TodoDatabase.getDatabase(TodoList.this).todoDao().AddTodo(deletedTodo);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        new MaterialDialog.Builder(this)
                .content("Do you want to Exit?")
                .title("Warning")
                .cancelable(false)
                .positiveText("Confirm")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    //    public void sortData(){
//
//        Collections.sort(AllTodos, new Comparator<Todo>() {
//            @Override
//            public int compare(Todo t1, Todo t2) {
//                if(t1.getDateTime() t2.getDateTime()){
//
//                }
//
//                return 0;
//            }
//        });
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
