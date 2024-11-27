package com.vishnu.todoapp.ui

import android.provider.Contacts.Intents.UI
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnu.todoapp.R
import com.vishnu.todoapp.data.Todo
import com.vishnu.todoapp.data.TodoRepository
import com.vishnu.todoapp.util.ADD_TODO_DELAY
import com.vishnu.todoapp.util.DEBOUNCE_DELAY
import com.vishnu.todoapp.util.Routes
import com.vishnu.todoapp.util.ToDoEvents
import com.vishnu.todoapp.util.UIEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.getAllTodos()

    var todoText by mutableStateOf("")
        private set
    private val _uiEvent =  Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredTodos = searchQuery
        .debounce(DEBOUNCE_DELAY)
        .combine(todos) { query, todos ->
            if(query.isBlank()) {
               todos
            }else if(query.contains("Error", ignoreCase = true)){
                sendTodoEvent(ToDoEvents.Error(R.string.failed_to_add_todo))
            }else{
                todos.filter {
                    it.todo.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList<Todo>())

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun sendTodoEvent(event: ToDoEvents) {
        when(event) {
            is ToDoEvents.SaveToDo -> {
                viewModelScope.launch {
                    repository.insertTodo(Todo(todo = todoText))
                }
            }

            is ToDoEvents.Error -> {
                viewModelScope.launch {
                    delay(ADD_TODO_DELAY)
                    sendUiEvent(UIEvent.PopBackStack)
                    sendUiEvent(UIEvent.ShowSnackBar(
                        message = event.message.toString()
                    ))
                }
            }

            ToDoEvents.AddTodo -> {
                viewModelScope.launch {
                    delay(ADD_TODO_DELAY)
                    sendUiEvent(UIEvent.Navigate(Routes.ADD_TODO))
                }
            }
        }
    }
}