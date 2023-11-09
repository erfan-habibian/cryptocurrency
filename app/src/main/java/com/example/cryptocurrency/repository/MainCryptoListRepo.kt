package com.example.cryptocurrency.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.cryptocurrency.io.interfaces.ApiInterface
import com.example.cryptocurrency.io.webService.RetrofitInstance
import com.example.cryptocurrency.model.Crypto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Callback

class MainCryptoListRepo {


    private val api = RetrofitInstance.getInstance().create(ApiInterface::class.java)

    private val listLveData : MutableLiveData<Crypto> by lazy {
        MutableLiveData<Crypto>()
    }

    fun getList(): MutableLiveData<Crypto>{
        CoroutineScope(Dispatchers.IO).launch {
            val result = api.getCryptoList()
            if (result.isSuccessful){
                launch(Dispatchers.Main){
                    listLveData.postValue(result.body())
                }

            }else{
                Log.e("ERROR_TAG", "error in getting crypto list")
            }
        }
        return listLveData
    }







}