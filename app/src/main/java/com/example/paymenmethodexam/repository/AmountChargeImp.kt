package com.example.paymenmethodexam.repository

import com.example.paymenmethodexam.data.network.SafeApiCall
import com.example.paymenmethodexam.data.remote.PaymentService
import com.example.paymenmethodexam.utils.Constants
import javax.inject.Inject

class AmountChargeImp @Inject constructor(
    private val paymentService: PaymentService
): SafeApiCall {

    suspend fun getPaymentMethod() = safeApiCall {
        paymentService.paymentMethod(Constants.PUBLIC_KEY_API)
    }

    suspend fun getBankCards(paymentMethodId: String) = safeApiCall {
        paymentService.getBank(Constants.PUBLIC_KEY_API, paymentMethodId)
    }

    suspend fun getInstallments(amount: Int, paymentMethodId: String, idBank: String) = safeApiCall {
        paymentService.getInstallments(Constants.PUBLIC_KEY_API, amount, paymentMethodId, idBank)
    }
}