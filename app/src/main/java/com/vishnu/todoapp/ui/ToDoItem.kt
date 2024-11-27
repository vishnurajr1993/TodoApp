package com.vishnu.todoapp.ui


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vishnu.todoapp.data.Todo

@Composable
fun TodoItem(
    todo: Todo,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = todo.todo,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}