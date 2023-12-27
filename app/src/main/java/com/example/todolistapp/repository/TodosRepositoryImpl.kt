package com.example.todolistapp.repository

import com.example.todolistapp.framework.room.dao.TodoDAO
import com.example.todolistapp.models.Todo
import javax.inject.Inject

class TodosRepositoryImpl @Inject constructor (private var todoDAO: TodoDAO) : TodosRepository {

    override suspend fun addNewTodo(todo: Todo) = todoDAO.addTodo(todo)

    override suspend fun updateTodo(todo: Todo) = todoDAO.updateTodo(todo)

    override suspend fun getAllTodos(): List<Todo> = todoDAO.readAllTodos()

    override suspend fun deleteTodo(todo: Todo) = todoDAO.deleteTodo(todo)
}