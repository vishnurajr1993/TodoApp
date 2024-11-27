package com.vishnu.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnu.todoapp.data.TodoRepository
import com.vishnu.todoapp.util.ADD_TODO_DELAY
import com.vishnu.todoapp.util.UIEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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

    private val _uiEvent =  Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val filteredTodos = searchQuery
        .debounce(ADD_TODO_DELAY)
        .combine(todos) { query, todos ->
            if(query.isBlank()) {
               todos
            }else{
                todos.filter {
                    it.todo.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}