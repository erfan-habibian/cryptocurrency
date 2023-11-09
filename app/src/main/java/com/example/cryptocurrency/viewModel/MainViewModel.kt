package com.example.cryptocurrency.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.model.Crypto
import com.example.cryptocurrency.repository.MainCryptoListRepo
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val repository: MainCryptoListRepo = MainCryptoListRepo()
    private var _list = MutableLiveData<Crypto>()

    val list : LiveData<Crypto>
        get() = _list

    fun fetchCryptoList() {
        _list = repository.getList()
    }




}