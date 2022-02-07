package com.example.to_do_list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ItemTaskBinding
import com.example.to_do_list.model.Task
import com.example.to_do_list.ui.TaskListAdapter.TaskViewHolder

class TaskListAdapter : ListAdapter<Task, TaskViewHolder>(DiffCallback()) {

    class TaskViewHolder(private val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task?) {
            binding.tvTitulo.text = item?.title
            binding.tvData.text = "${item?.hour} ${item?.date}"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback : DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id

}