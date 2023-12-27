package com.example.todolistapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemTodolistBinding
import com.example.todolistapp.databinding.LayoutPopupMoreOptionTodolistBinding
import com.example.todolistapp.models.Todo

class ListTodoAdapter(private var context: Context,
                      var onChangeTodoListener: OnChangeTodoListener
) :ListAdapter<Todo, ListTodoAdapter.TodoViewHolder>(DIFFUTILS){

    private var popupMenu :PopupWindow? = null

    inner class TodoViewHolder(private var binding :ItemTodolistBinding) :RecyclerView.ViewHolder(binding.root){

        fun bind(todo : Todo){
            binding.todoMore.setOnClickListener {
                showMoreOptionPopupDialog(todo, it)
            }
            binding.cbTodo.setOnCheckedChangeListener { _, b ->
                todo.isChecked = b
                onChangeTodoListener.onDoUpdateTodo(todo)
            }
            binding.labelTodo.text = todo.todo
            binding.cbTodo.isChecked = todo.isChecked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = ItemTodolistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun showMoreOptionPopupDialog(todo: Todo, anchor :View){
        if (popupMenu == null) popupMenu = PopupWindow(context)
        val viewMenu = LayoutPopupMoreOptionTodolistBinding.inflate(LayoutInflater.from(context))
        viewMenu.btnEdit.setOnClickListener {
            popupMenu?.dismiss()
            onChangeTodoListener.onDoEditTodo(todo)
        }
        viewMenu.btnDelete.setOnClickListener {
            popupMenu?.dismiss()
            onChangeTodoListener.onDoDeleteTodo(todo)
        }
        popupMenu?.contentView = viewMenu.root
        popupMenu?.isOutsideTouchable = true
        popupMenu?.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.white))
        popupMenu?.elevation = 20F
        popupMenu?.let {
            if (it.isShowing) popupMenu?.dismiss()
            popupMenu?.showAsDropDown(anchor)
        }

    }

    companion object{
        var DIFFUTILS = object :DiffUtil.ItemCallback<Todo>(){
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }


}