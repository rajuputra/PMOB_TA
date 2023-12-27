package com.example.todolistapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.models.Todo
import com.example.todolistapp.repository.TodosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var todosRepository: TodosRepository) :ViewModel(){

    private val todoListLiveData = MutableLiveData<List<Todo>>()

    fun addAnRefresh(todo: Todo) = viewModelScope.launch {
        addTodo(todo).join()
        loadAllTodoList().join()
    }

    fun updateAndRefresh(todo: Todo) = viewModelScope.launch {
        updateTodolist(todo).join()
        loadAllTodoList().join()
    }

    fun deleteAndRefresh(todo: Todo) = viewModelScope.launch {
        deleteTodo(todo).join()
        loadAllTodoList().join()
    }

    private fun deleteTodo(todo: Todo) = viewModelScope.launch {
        todosRepository.deleteTodo(todo)
    }

    private fun addTodo(todo: Todo) = viewModelScope.launch {
        todosRepository.addNewTodo(todo)
    }

    fun loadAllTodoList() = viewModelScope.launch {
        todoListLiveData.postValue(todosRepository.getAllTodos())
    }

    private fun updateTodolist(todo: Todo) = viewModelScope.launch {
        todosRepository.updateTodo(todo)
    }

    fun observeTodoList() = todoListLiveData

}