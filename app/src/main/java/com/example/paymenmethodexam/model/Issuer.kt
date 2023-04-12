package com.example.paymenmethodexam.model

import com.google.gson.annotations.SerializedName

data class Issuer(
    @SerializedName("id") var id: String,
    @SerializedName("name") var name: String,
    @SerializedName("secure_thumbnail") var secureThumbnail: String,
    @SerializedName("thumbnail") var thumbnail: String
)
