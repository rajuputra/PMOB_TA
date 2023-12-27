package com.example.todolistapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.models.Todo
import com.example.todolistapp.ui.addtodo.UpdateTodoListDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.todolistapp.R
import com.example.todolistapp.ui.maps.MapsActivity

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnChangeTodoListener {

    private lateinit var binding :ActivityMainBinding
    private lateinit var listAdapter : ListTodoAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar yang barada pada top page
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        //untuk menampilkan icon maps pada toolbar menu_maps
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_maps, binding.toolbar.menu)
        binding.toolbar.setOnMenuItemClickListener { item->
            when(item.itemId) {
                R.id.action_location -> {
                    Log.d("MainActivity", "Clicked on action_location menu item")
                    val maps = Intent(this@MainActivity, MapsActivity::class.java)
                    startActivity(maps)

                    // Ganti judul Toolbar di sini
                    title = "Your Location"

                    true
                }
                else -> false
            }
        }

        setupObservable()
        setupView()
        viewModel.loadAllTodoList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_maps, menu)
        return true
    }

    private fun setupView(){
        setupList()
        //untuk menambahkan todo yang akan memanggil button float btnaddtodo
        binding.btnAddTodo.setOnClickListener { showAddTodoDialog() }
    }

    //show dialog add
    private fun showAddTodoDialog(){
        val addTodoDialog = UpdateTodoListDialogFragment(isAddTodoForm = true, this)
        addTodoDialog.show(supportFragmentManager,"Add Dialog")
    }

    //show dialog edit
    private fun showEditTodoDialog(todo: Todo){
        val editTodoDialog = UpdateTodoListDialogFragment(isAddTodoForm = false, this, todo)
        editTodoDialog.show(supportFragmentManager, "Edit Dialog")
    }

    //yang akan menampilkan alert todo utnuk delete serta
    //menampilkan button text yes & no for delete todo
    private fun showDeleteConfirmationTodoDialog(todo: Todo){
        AlertDialog.Builder(this)
            .setTitle("Delete Todo")
            .setMessage("Are you sure want to delete \"${todo.todo}\" ?")
            .setPositiveButton("Yes"){ dialog,_ ->
                dialog.dismiss()
                viewModel.deleteAndRefresh(todo)
            }.setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun setupList(){
        //todo : yang akan menampilkan list dengan call rcTodos dan call adapter for show list
        listAdapter = ListTodoAdapter(this, this)
        binding.rvTodos.adapter = listAdapter
        binding.rvTodos.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservable(){
        viewModel.observeTodoList().observe(this){
            //todo: make checkbox checklist or not pada list todo
            listAdapter.submitList(it.sortedBy { todo -> todo.isChecked })
            showEmptyText(it.isEmpty())
        }
    }

    private fun showEmptyText(show :Boolean){
        binding.emptyLayout.root.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvTodos.visibility = if (show) View.GONE else View.VISIBLE
    }

    //METHODE yang akan melakukan fitur
    override fun onDoUpdateTodo(todo: Todo) {
        viewModel.updateAndRefresh(todo)
    }

    override fun onDoAddTodo(todo: Todo) {
        viewModel.addAnRefresh(todo)
    }

    override fun onDoEditTodo(todo: Todo) {
        showEditTodoDialog(todo)
    }

    override fun onDoDeleteTodo(todo: Todo) {
        showDeleteConfirmationTodoDialog(todo)
    }



}