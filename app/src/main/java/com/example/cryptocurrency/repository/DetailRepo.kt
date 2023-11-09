package com.example.cryptocurrency.repository

import CryptoDetail
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrency.io.interfaces.ApiInterface
import com.example.cryptocurrency.io.webService.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailRepo {

    private val api = RetrofitInstance.getInstance().create(ApiInterface::class.java)

    private val detailLiveData : MutableLiveData<CryptoDetail> by lazy {
        MutableLiveData<CryptoDetail>()
    }


    fun getDetail(id: Int) : MutableLiveData<CryptoDetail>{
        CoroutineScope(Dispatchers.IO).launch {
            val result = api.getDetail()
            if (result.isSuccessful){
                launch(Dispatchers.Main){
                    detailLiveData.postValue(result.body())
                }
            }else{
                Log.e("ERROR_TAG", "error in getting crypto detail")
            }
        }
        return detailLiveData
    }

}