package com.vishnu.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val todo: String,
    @PrimaryKey val id: Int? = null
)
