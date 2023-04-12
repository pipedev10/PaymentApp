package com.example.paymenmethodexam.data.remote

import com.example.paymenmethodexam.model.PaymentMethod
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentService {

    @GET("payment_methods")
    suspend fun paymentMethod(
        @Query("public_key") publicKey: String
    ): List<PaymentMethod>
}