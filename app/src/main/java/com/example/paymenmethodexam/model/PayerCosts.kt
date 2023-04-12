package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class PayerCosts(
    @SerializedName("installments") var installments: Int,
    @SerializedName("installment_rate") var installmentRate: Double,
    @SerializedName("discount_rate") var discountRate: Int,
    @SerializedName("reimbursement_rate") var reimbursementRate: String,
    @SerializedName("labels") var labels: List<String>,
    @SerializedName("installment_rate_collector") var installmentRateCollector: List<String>,
    @SerializedName("min_allowed_amount") var minAllowedAmount: Int,
    @SerializedName("max_allowed_amount") var maxAllowedAmount: Int,
    @SerializedName("recommended_message") var recommendedMessage: String,
    @SerializedName("installment_amount") var installmentAmount: Double,
    @SerializedName("total_amount") var totalAmount: Double,
    @SerializedName("payment_method_option_id") var paymentMethodOptionId: String
)
