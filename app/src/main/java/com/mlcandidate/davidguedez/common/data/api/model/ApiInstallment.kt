package com.mlcandidate.davidguedez.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiInstallment(
    @field:Json(name = "quantity") val quantity: Int?,
    @field:Json(name = "amount") val amount: Float?
)
