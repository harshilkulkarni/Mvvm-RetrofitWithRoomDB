package com.jetpack.mvvmretrofitquote.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jetpack.mvvmretrofitquote.repository.QuoteRespository

class MainViewModelFactory(private val repository: QuoteRespository, private val i: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository, i) as T
    }

}