package com.vishnu.todoapp.di

import android.app.Application
import androidx.room.Room
import com.vishnu.todoapp.data.ToDoRepositoryImpl
import com.vishnu.todoapp.data.TodoDatabase
import com.vishnu.todoapp.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return ToDoRepositoryImpl(db.dao)
    }

}