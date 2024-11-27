package com.vishnu.todoapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<Todo>>
}