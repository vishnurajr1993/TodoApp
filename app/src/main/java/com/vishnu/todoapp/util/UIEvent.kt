package com.vishnu.todoapp.util

sealed class UIEvent {
    object ShowLoader : UIEvent()
    object HideLoader : UIEvent()
    object PopBackStack : UIEvent()
    data class Navigate(val route: String): UIEvent()
    data class ShowSnackBar(
        val message: Int,
    ): UIEvent()
}