package com.example.to_do_list.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.R
import com.example.to_do_list.datasource.TaskDataSource
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvTasks = findViewById<RecyclerView>(R.id.rv_tasks)
        rvTasks.adapter = adapter
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
        adapter.submitList(TaskDataSource.getList())
    }


    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
    }