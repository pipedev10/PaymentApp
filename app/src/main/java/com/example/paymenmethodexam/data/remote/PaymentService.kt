package com.example.paymenmethodexam.data.remote

import com.example.paymenmethodexam.model.BankCard
import com.example.paymenmethodexam.model.Installments
import com.example.paymenmethodexam.model.PaymentMethod
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentService {

    @GET("payment_methods")
    suspend fun paymentMethod(
        @Query("public_key") publicKey: String
    ): List<PaymentMethod>

    @GET("payment_methods/card_issuers")
    suspend fun getBank(
        @Query("public_key") publicKey: String,
        @Query("payment_method_id") paymentMethodId: String
    ): List<BankCard>

    @GET("payment_methods/installments")
    suspend fun getInstallments(
        @Query("public_key") publicKey: String,
        @Query("amount") amount: Int,
        @Query("payment_method_id") paymentMethodId: String,
        @Query("issuer.id") issuer: String
    ): List<Installments>
}