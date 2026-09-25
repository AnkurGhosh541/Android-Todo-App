package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.local.TaskItem
import com.example.todoapp.ui.components.TaskDeleteDialog
import com.example.todoapp.ui.components.TaskEditorDialog
import com.example.todoapp.ui.components.TodoItem
import com.example.todoapp.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.allTasks.collectAsState()

    var taskToEdit by remember { mutableStateOf<TaskItem?>(null) }

    var taskToDelete by remember { mutableStateOf<TaskItem?>(null) }

    var showEditDialog by remember { mutableStateOf(false) }

    var showAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "My Tasks",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            "${tasks.filter { !it.isDone }.size} remaining today",
                            fontSize = 16.sp
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    taskToEdit = null
                    showEditDialog = true
                },
                shape = RoundedCornerShape(20.dp),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "New Task")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (tasks.isEmpty()) {
                Text(
                    text = "No tasks today. Add some now.",
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        items = tasks,
                        key = { it.id }
                    ) { task ->
                        TodoItem(
                            item = task,
                            onEdit = {
                                taskToEdit = task
                                showEditDialog = true
                            },
                            onDelete = {
                                taskToDelete = task
                                showAlertDialog = true
                            },
                            onCheckedChange = { checked ->
                                viewModel.updateTask(task.copy(isDone = checked))
                            }
                        )
                    }
                }
            }

            if (showEditDialog) {
                TaskEditorDialog(
                    task = taskToEdit,
                    onSave = { newTask ->
                        if (taskToEdit == null) {
                            viewModel.addTask(TaskItem(task = newTask))
                        } else {
                            viewModel.updateTask(taskToEdit!!.copy(task = newTask))
                        }
                        taskToEdit = null
                        showEditDialog = false
                    },
                    onCancel = {
                        taskToEdit = null
                        showEditDialog = false
                    }
                )
            }

            if (showAlertDialog) {
                TaskDeleteDialog(
                    task = taskToDelete,
                    onDismiss = {
                        showAlertDialog = false
                    },
                    onCancel = {
                        taskToDelete = null
                        showAlertDialog = false
                    },
                    onDelete = {
                        viewModel.deleteTask(taskToDelete!!)
                        taskToDelete = null
                        showAlertDialog = false
                    }
                )

            }
        }
    }
}

