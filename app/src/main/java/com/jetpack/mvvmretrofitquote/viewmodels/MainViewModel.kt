package com.jetpack.mvvmretrofitquote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.mvvmretrofitquote.models.QuoteList
import com.jetpack.mvvmretrofitquote.repository.QuoteRespository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuoteRespository, private val i: Int) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getQuotes(i)
        }
    }

    val quotes: LiveData<QuoteList>
        get() = repository.quotes
}