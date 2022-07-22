package com.mlcandidate.davidguedez.common.data.api

import com.mlcandidate.davidguedez.common.domain.model.SearchProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface APIProductService {

    @GET("/sites/{siteId}/search")
    suspend fun searchProduct(@Path("siteId") siteId: String): SearchProduct
}