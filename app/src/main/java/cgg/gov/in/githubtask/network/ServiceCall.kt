package cgg.gov.`in`.githubtask.network

import cgg.gov.`in`.githubtask.model.ServiceCallRes
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ServiceCall {

    companion object {
        const val BASE_URL = "https://api.github.com/"

        fun create(): ServiceCall {
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ServiceCall::class.java)
        }
    }


    @GET("search/repositories?sort=stars")
    fun searchRepositories(
    @Query("q") query: String,
    @Query("page") page: Int,
    @Query("per_page") itemsPerPage: Int
    ): Call<ServiceCallRes>


}