package com.example.to_do_list.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_list.R
import com.example.to_do_list.databinding.ActivityAddTaskBinding
import com.example.to_do_list.datasource.TaskDataSource
import com.example.to_do_list.extensions.format
import com.example.to_do_list.extensions.text
import com.example.to_do_list.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners(){
        binding.tilDate.editText?.setOnClickListener {
           val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) *-1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute

                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.tilHour.text = "$hour: $minute"
            }

            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                hour = binding.tilHour.text
            )
            TaskDataSource.insertTask(task)

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}