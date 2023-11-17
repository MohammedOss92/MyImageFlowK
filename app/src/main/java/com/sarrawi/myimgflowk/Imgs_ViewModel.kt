package com.sarrawi.myimgflowk

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.sarrawi.img.model.ImgsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class Imgs_ViewModel(private val context: Context,  private val imgsRepo:ImgRepository ): ViewModel() {

    private val retrofitServices = ApiService.provideRetrofitInstance()



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading







    fun getAllImgsViewModel(): LiveData<List<ImgsModel>> {
        _isLoading.postValue(true) // عرض ProgressBar قبل بدء التحميل

        val _response = MutableLiveData<List<ImgsModel>>()

        viewModelScope.launch {
            try {
                val response = imgsRepo.getAllImgs_Repo()

                if (response.isSuccessful) {
                    val results = response.body()?.results
                    _response.postValue(results)
                    Log.i("TestRoom", "getAllImgs: posts $results")
//                    imgsRepo.insert_imgs_repo(response.body()?.results)
                } else {
                    Log.i("TestRoom", "getAllImgs: data corrupted")
                    Log.d("tag", "getAll Error: ${response.code()}")
                    Log.d("tag", "getAll: ${response.body()}")
                }
            } catch (e: Exception) {
                Log.e("TestRoom", "getAllImgs: Error: ${e.message}")
            } finally {
                _isLoading.postValue(false) // إخفاء ProgressBar بعد انتهاء التحميل
            }
        }

        return _response
    }

    fun getAllImgsViewModelflow(): LiveData<List<ImgsModel>> {
        _isLoading.postValue(true)

        val _response = MutableLiveData<List<ImgsModel>>()

        viewModelScope.launch {
            imgsRepo.getAllImgsRepo().collect { results ->
                _response.postValue(results)
                _isLoading.postValue(false)
            }
        }

        return _response
    }

    private val _response = MutableStateFlow<List<ImgsModel>>(emptyList())
    val response: StateFlow<List<ImgsModel>>
        get() = _response


    init {
        viewModelScope.launch {
            imgsRepo.getAllImgsRepo().collect { results ->
                _response.value = results
                _isLoading.value = false
            }
        }



    }











}