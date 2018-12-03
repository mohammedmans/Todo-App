package com.example.mohammedmansour.mytodo;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.mohammedmansour.mytodo.Database.TodoDatabase;
import com.example.mohammedmansour.mytodo.Model.Todo;

import java.security.PublicKey;

public class AddTodo extends AppCompatActivity {

    Button Add;
    EditText title, desc, datetime;
    public static Todo todoItem = null;
    Button update_btn, delete_btn;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        todoItem = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(todoItem != null){
            String activityTitle = getIntent().getStringExtra("update_todo");
            setTitle(activityTitle);
        }
        Add = findViewById(R.id.add);
        title = findViewById(R.id.title);
//        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(title,inputManager.SHOW_IMPLICIT);

        desc = findViewById(R.id.desc);
        datetime = findViewById(R.id.dateTime);
        update_btn = findViewById(R.id.update);
        delete_btn = findViewById(R.id.delete);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        showUpdDel();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodo();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateTodo();
            }
        });
    }


    public void addTodo() {
        String todoTitle = title.getText().toString();
        String todoDesc = desc.getText().toString();
        String todoTime = datetime.getText().toString();
        Todo todo = new Todo(todoTitle, todoDesc, todoTime);
        TodoDatabase.getDatabase(this).todoDao().AddTodo(todo);
        finish();
    }

    public void showUpdDel() {
        if (todoItem != null) {
            title.setText(todoItem.getTitle());
            desc.setText(todoItem.getContent());
            datetime.setText(todoItem.getDateTime());
            update_btn.setVisibility(View.VISIBLE);
            delete_btn.setVisibility(View.VISIBLE);
            Add.setVisibility(View.GONE);
        }
    }

    public void deleteTodo() {
        TodoDatabase.getDatabase(this).todoDao().DeleteTodo(todoItem);
        todoItem = null;
        finish();
    }

    public void updateTodo() {
        String todoTitle = title.getText().toString();
        String todoDesc = desc.getText().toString();
        String todoTime = datetime.getText().toString();
        //Todo todo = new Todo(todoTitle, todoDesc, todoTime);
        todoItem.setTitle(todoTitle);
        todoItem.setContent(todoDesc);
        todoItem.setDateTime(todoTime);

        TodoDatabase.getDatabase(this).todoDao().UpdateTodo(todoItem);
        todoItem = null;
        finish();
    }

}
