package com.example.paymenmethodexam.data.network

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.example.paymenmethodexam.utils.ResultCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataSource @Inject constructor() {

    companion object {
        private const val TIMEOUT = 30L

        private const val BASE_PAYMENT_URL = "https://api.mercadopago.com/v1/"
    }

    fun <Api> buildPaymentApi(
        api: Class<Api>,
        context: Context
    ): Api {

        val builder = OkHttpClient.Builder()

        builder.addInterceptor(Interceptor { chain ->
            val original = chain.request();

            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()

            chain.proceed(request);
        })
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)

        val client: OkHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_PAYMENT_URL)
            .client(getRetrofitClient(null))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(client)
            .build()
            .create(api)
    }

    private fun getRetrofitClient(authenticator: Authenticator? = null): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                authenticator?.let { client.authenticator(it) }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}