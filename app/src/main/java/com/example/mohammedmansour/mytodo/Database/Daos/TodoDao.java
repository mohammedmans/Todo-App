package com.example.mohammedmansour.mytodo.Database.Daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.mohammedmansour.mytodo.Model.Todo;

import java.util.List;

// this is access object for our model (Todo)
@Dao
public interface TodoDao {

    @Insert
    void AddTodo(Todo todo);

    @Delete
    void DeleteTodo (Todo todo);

    @Update
    void UpdateTodo (Todo todo);

    @Query("select * from Todo")
    List<Todo>getAllTodo();

    @Query("select * from todo where id =:id")
    Todo getItemById(int id);
}
