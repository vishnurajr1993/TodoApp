package com.vishnu.todoapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishnu.todoapp.R
import com.vishnu.todoapp.util.ToDoEvents
import com.vishnu.todoapp.util.UIEvent

@Composable
fun AddToDoScreen(
    onPopBackStack: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.todoText,
                onValueChange = {
                    viewModel.sendTodoEvent(ToDoEvents.OnToDoTextChange(it))
                },
                placeholder = {
                    Text(text = "Type todo here..")
                },
                label = {},
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.sendTodoEvent(ToDoEvents.SaveToDo)
            }
            ) {
                Text(text = stringResource(R.string.save_todo),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }
}