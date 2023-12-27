package com.example.todolistapp.ui.addtodo

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.todolistapp.databinding.LayoutFragmentUpdateTodoBinding
import com.example.todolistapp.models.Todo
import com.example.todolistapp.ui.main.OnChangeTodoListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateTodoListDialogFragment(var isAddTodoForm :Boolean = false,
                                   private var onChangeTodoListener: OnChangeTodoListener,
                                   var todo: Todo? = null) :DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutFragmentUpdateTodoBinding.inflate(layoutInflater)
        view.inputTodo.setText(todo?.todo)

        return AlertDialog.Builder(requireContext())
            .setTitle(if (isAddTodoForm) "Add New Todo" else "Update Todo")
            .setView(view.root)
            .setPositiveButton(if (isAddTodoForm) "Save" else "Update"){ dialog , _ ->
                dialog.dismiss()
                if (!isAddTodoForm){
                    todo?.let { todo ->
                        todo.todo = view.inputTodo.text.toString()
                        onChangeTodoListener.onDoUpdateTodo(todo)
                    }
                }else onChangeTodoListener.onDoAddTodo(Todo(todo = view.inputTodo.text.toString(), isChecked = false))
            }.setNegativeButton("Cancel"){ dialog , _ ->
                dialog.dismiss()
            }.create()
    }


}