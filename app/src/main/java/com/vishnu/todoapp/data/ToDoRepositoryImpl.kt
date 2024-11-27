package com.vishnu.todoapp.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl (private val dao: TodoDao) : TodoRepository {

    override suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    override fun getAllTodos(): Flow<List<Todo>> = dao.getTodos()

}