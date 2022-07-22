package com.mlcandidate.davidguedez.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiShipping(
    @field:Json(name = "free_shipping") val freeShipping: Boolean?
)
