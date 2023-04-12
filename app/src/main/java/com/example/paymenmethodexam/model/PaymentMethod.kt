package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class PaymentMethod(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("payment_type_id") var paymentTypeId: String,
    @SerializedName("status") var status: String,
    @SerializedName("secure_thumbnail") var secureThumbnail: String,
    @SerializedName("thumbnail") var thumbnail: String,
    @SerializedName("deferred_capture") var deferredCapture: String
)
