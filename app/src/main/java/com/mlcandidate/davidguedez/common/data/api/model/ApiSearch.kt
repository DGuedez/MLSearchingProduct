package com.mlcandidate.davidguedez.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiSearch(
    @field:Json(name = "query") val query: String?,
    @field:Json(name = "results") val productResults: List<ApiProduct>?
)