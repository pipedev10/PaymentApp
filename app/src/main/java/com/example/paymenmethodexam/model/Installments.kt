package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class Installments(
    @SerializedName("payment_method_id") var paymentMethodId: String,
    @SerializedName("payment_type_id") var paymentTypeId: String,
    @SerializedName("issuer") var issuer: Issuer,
    @SerializedName("processing_mode") var processingMode: String,
    @SerializedName("merchant_account_id") var merchantAccountId: String,
    @SerializedName("payer_costs") var payerCosts: List<PayerCosts>,
    @SerializedName("agreements") var agreements: String
)
