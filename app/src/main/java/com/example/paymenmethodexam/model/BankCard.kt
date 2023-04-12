package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class BankCard(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("secure_thumbnail") var secureThumbnail: String,
    @SerializedName("thumbnail") var thumbnail: String,
    @SerializedName("processing_mode") var processingMode: String,
    @SerializedName("merchant_account_id") var merchantAccountId: String,
    @SerializedName("status") var status: String
): java.io.Serializable
