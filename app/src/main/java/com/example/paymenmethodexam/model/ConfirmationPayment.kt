package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class ConfirmationPayment(
    @SerializedName("amount") var amount: Int,
    @SerializedName("payment_method_name") var paymentMethod: String,
    @SerializedName("bank") var bank: String,
    @SerializedName("installment") var installment: String,
    @SerializedName("name_user") var nameUser: String
)
