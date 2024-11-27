package com.vishnu.todoapp.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    fun getAllTodos(): Flow<List<Todo>>
}