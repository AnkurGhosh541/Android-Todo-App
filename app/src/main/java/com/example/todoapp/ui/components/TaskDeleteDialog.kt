package com.example.todoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.local.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDeleteDialog(
    task: TaskItem?,
    onDismiss: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text =
                        "Do you want to delete this task?"
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                ) {
                    TextButton(
                        onClick = onCancel
                    ) {
                        Text("Cancel")
                    }

                    TextButton(
                        onClick = onDelete,
                    ) {
                        Text("Delete", color = Color.Red)
                    }
                }
            }
        }
    }
}