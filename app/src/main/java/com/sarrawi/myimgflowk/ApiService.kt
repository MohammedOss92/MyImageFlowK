package com.sarrawi.myimgflowk

import com.sarrawi.img.model.ImgsModel
import com.sarrawi.img.model.ImgsRespone
import com.sarrawi.img.model.MyImgsRespone
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

interface ApiService {



    @GET("imgs_api")
    suspend fun getAllImgs(): Response<ImgsRespone>



    companion object {

        private var retrofitService: ApiService? = null

        fun provideRetrofitInstance(): ApiService {
            if (retrofitService == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    // أي إعدادات إضافية لـ OkHttpClient يمكنك إضافتها هنا
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("http://www.sarrawi.bio/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(ApiService::class.java)
            }
            return retrofitService!!


        }
    }
}