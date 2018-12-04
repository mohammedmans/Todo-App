package com.example.mohammedmansour.mytodo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mohammedmansour.mytodo.Database.TodoDatabase;
import com.example.mohammedmansour.mytodo.Model.Todo;
import com.example.mohammedmansour.mytodo.Receivers.TodoAlarmBroadcastReceiver;

import java.security.PublicKey;
import java.util.Calendar;

public class AddTodo extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button Add;
    EditText title, desc;
    TextView datetime;
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
        if (todoItem != null) {
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
        datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePickerDialog();
            }
        });
    }


    public void addTodo() {
        String todoTitle = title.getText().toString();
        String todoDesc = desc.getText().toString();
        String todoTime = datetime.getText().toString();
        Todo todo = new Todo(todoTitle, todoDesc, todoTime);
        TodoDatabase.getDatabase(this).todoDao().AddTodo(todo);
        StartAlarm();
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
        StartAlarm();
        todoItem = null;
        finish();
    }

    public void ShowTimePickerDialog() {

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(this, this,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                false);
        dialog.show();

    }

    int TaskHour, TaskMinute;

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        TaskHour = hour;
        TaskMinute = minute;
        datetime.setText(hour + ":" + minute);
    }

    public void StartAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, TaskHour);
        calendar.set(Calendar.MINUTE, TaskMinute);
        calendar.set(Calendar.SECOND, 0);

        Intent i = new Intent(this, TodoAlarmBroadcastReceiver.class);
        i.putExtra("title", title.getText().toString());
        i.putExtra("desc", desc.getText().toString());
        PendingIntent pi =
                PendingIntent.getBroadcast(this, 500, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }
}
