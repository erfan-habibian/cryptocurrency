package com.example.cryptocurrency.viewModel

import CryptoDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrency.repository.DetailRepo

class DetailViewModel: ViewModel() {

    private val repository = DetailRepo()

    private var _detail = MutableLiveData<CryptoDetail>()

    val detail : LiveData<CryptoDetail>
        get() = _detail

    fun fetchDetail(id: Int){
        _detail = repository.getDetail(id)
    }



}