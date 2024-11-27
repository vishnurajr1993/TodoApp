package com.vishnu.todoapp.util

import com.vishnu.todoapp.data.Todo

sealed class ToDoEvents{
    object AddTodo : ToDoEvents()
    object SaveToDo : ToDoEvents()
    data class Error(val message : Int) : ToDoEvents()
    data class OnToDoTextChange(val text: String) : ToDoEvents()
    data class SearchQuery(val query : String) : ToDoEvents()
}