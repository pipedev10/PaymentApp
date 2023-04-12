package com.example.paymenmethodexam.repository

import com.example.paymenmethodexam.data.network.SafeApiCall
import com.example.paymenmethodexam.data.remote.PaymentService
import javax.inject.Inject

class AmountChargeImp @Inject constructor(
    private val paymentService: PaymentService
): SafeApiCall {

    suspend fun getPaymentMethod() = safeApiCall {
        paymentService.paymentMethod("444a9ef5-8a6b-429f-abdf-587639155d88")
    }
}