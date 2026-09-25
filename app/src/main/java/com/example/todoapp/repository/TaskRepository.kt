package com.example.todoapp.repository

import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.local.TaskItem

class TaskRepository(private val dao: TaskDao) {
    fun getAllTasks() = dao.getAllTasks()

    suspend fun insert(task: TaskItem) = dao.insertTask(task)

    suspend fun update(task: TaskItem) = dao.updateTask(task)

    suspend fun delete(task: TaskItem) = dao.deleteTask(task)
}