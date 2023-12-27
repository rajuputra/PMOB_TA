package com.example.todolistapp.framework.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.framework.room.dao.TodoDAO
import com.example.todolistapp.models.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase :RoomDatabase() {
    abstract fun todoDao() : TodoDAO
}