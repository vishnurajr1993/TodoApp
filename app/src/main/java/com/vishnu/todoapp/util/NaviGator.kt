package com.vishnu.todoapp.util

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishnu.todoapp.ui.AddToDoScreen
import com.vishnu.todoapp.ui.TodoListScreen
import com.vishnu.todoapp.ui.TodoViewModel


@Composable
fun Navigator(){
    val navController = rememberNavController()
    val todoViewModel : TodoViewModel = hiltViewModel()
    NavHost(navController = navController,
        startDestination = Routes.TODO_LIST)  {
        composable(route = Routes.TODO_LIST){
            TodoListScreen(
                viewModel = todoViewModel,
                onNavigate = {
                navController.navigate(it.route)
            })
        }
        composable(route = Routes.ADD_TODO){
            AddToDoScreen(onPopBackStack = {
                navController.navigateUp()
            }, viewModel = todoViewModel)
        }
    }
}