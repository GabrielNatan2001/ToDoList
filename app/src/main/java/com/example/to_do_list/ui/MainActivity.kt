package com.example.to_do_list.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.R
import com.example.to_do_list.databinding.ActivityAddTaskBinding
import com.example.to_do_list.databinding.ActivityMainBinding
import com.example.to_do_list.datasource.TaskDataSource
import com.example.to_do_list.model.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.empty_state.view.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { TaskListAdapter() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val rvTasks = findViewById<RecyclerView>(R.id.rv_tasks)
        rv_tasks.adapter = adapter
        insertListeners()
        updateList()
    }

    fun insertListeners(){

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
            updateList()
        }
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CREATE_NEW_TASK){
            val rvTasks = findViewById<RecyclerView>(R.id.rv_tasks)
            rvTasks.adapter = adapter
            updateList()
        }
    }

    private fun updateList(){
        val list = TaskDataSource.getList()
        if(list.isEmpty()){
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        }else{
            binding.includeEmpty.emptyState.visibility = View.GONE
        }
        adapter.submitList(list)
    }


    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}