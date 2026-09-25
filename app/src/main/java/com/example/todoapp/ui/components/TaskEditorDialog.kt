package com.example.todoapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.local.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorDialog(
    task: TaskItem?,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {

    val taskState = TextFieldState(task?.task ?: "")

    ModalBottomSheet(
        onDismissRequest = onCancel,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .navigationBarsPadding()
        ) {
            Text(
                text = if (task == null) "Create New Task" else "Edit Task",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                state = taskState,
                placeholder = { Text("What needs to be done?") },
                shape = RoundedCornerShape(12.dp),
                lineLimits = TextFieldLineLimits.MultiLine(
                    minHeightInLines = 5,
                    maxHeightInLines = 10
                ),
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { onSave(taskState.text.trim().toString()) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = taskState.text.toString().isNotBlank()
            ) {
                Text(
                    "Save Task",
                    fontSize = 18.sp
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

