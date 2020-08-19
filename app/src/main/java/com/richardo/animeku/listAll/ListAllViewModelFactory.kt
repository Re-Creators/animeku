package com.richardo.animeku.listAll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ListAllViewModelFactory(private val sortType : String) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ListAllViewModel::class.java)){
            return ListAllViewModel(sortType) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }

}