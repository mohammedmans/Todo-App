package com.example.mohammedmansour.mytodo.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mohammedmansour.mytodo.Database.Daos.TodoDao;
import com.example.mohammedmansour.mytodo.Model.Todo;

@Database(entities = {Todo.class},version = 1,exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    // create object from DAO
    public abstract TodoDao todoDao();

    // create single pattern
    // 1- create private static instance of TodoDatabase
    private static TodoDatabase myDatabase;

    // 2- create fn that check if we have one object from Database or not have
    public static TodoDatabase getDatabase(Context context){
        if(myDatabase == null){
            // build database
            myDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class,"Todo-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return myDatabase;
    }

}

