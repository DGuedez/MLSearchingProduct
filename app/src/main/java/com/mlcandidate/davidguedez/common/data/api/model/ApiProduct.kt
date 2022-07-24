package com.mlcandidate.davidguedez.common.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiProduct(
    @field:Json(name = "id") val id: String?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "price") val price: Int?,
    @field:Json(name = "thumbnail") val thumbnail: String?,
    @field:Json(name = "installments")val installments: ApiInstallment?,
    @field:Json(name = "permalink")val detailsUrl: String?
)