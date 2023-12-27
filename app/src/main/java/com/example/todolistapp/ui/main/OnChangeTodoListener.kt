package com.example.todolistapp.ui.main

import com.example.todolistapp.models.Todo

//class yang berisikan fungsi dari fitur yang berasal dari table class todo
interface OnChangeTodoListener {
    fun onDoUpdateTodo(todo : Todo)
    fun onDoAddTodo(todo: Todo)
    fun onDoEditTodo(todo: Todo)
    fun onDoDeleteTodo(todo: Todo)
}