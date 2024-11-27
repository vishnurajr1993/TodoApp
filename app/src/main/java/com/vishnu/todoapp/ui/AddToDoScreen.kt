package com.vishnu.todoapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    viewModel: TodoViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = context.getString(event.message),
                        duration = SnackbarDuration.Short
                    )
                }
                is UIEvent.ShowLoader -> isLoading = true
                is UIEvent.HideLoader -> isLoading = false
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
        Box(contentAlignment = Alignment.Center) {
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
            if(isLoading){
                CircularProgressIndicator()
            }

        }
    }
}
