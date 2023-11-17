package com.sarrawi.myimgflowk

import android.app.Application
import android.util.Log
import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.ImgsRespone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


class ImgRepository(val apiService: ApiService,app:Application) {

    suspend fun getAllImgs_Repo() = apiService.getAllImgs()



    fun getAllImgsRepo(): Flow<List<ImgsModel>> = flow {
        try {
            val response = apiService.getAllImgs()

            if (response.isSuccessful) {
                val results = response.body()?.results
                emit(results ?: emptyList())
            } else {
                // يمكنك إلقاء استثناء هنا أو إرسال قيمة خاصة بالخطأ
                Log.i("TestRoom", "getAllImgsRepo: data corrupted")
                Log.d("tag", "getAll Error: ${response.code()}")
                Log.d("tag", "getAll: ${response.body()}")
            }
        } catch (e: Exception) {
            // يمكنك إلقاء استثناء هنا أو إرسال قيمة خاصة بالخطأ
            Log.e("TestRoom", "getAllImgsRepo: Error: ${e.message}")
        }
    }






}

