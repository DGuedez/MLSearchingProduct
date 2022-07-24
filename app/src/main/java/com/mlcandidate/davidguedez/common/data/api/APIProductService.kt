package com.mlcandidate.davidguedez.common.data.api

import com.mlcandidate.davidguedez.common.data.api.model.ApiSearchProduct

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIProductService {

    @GET("/sites/{siteId}/search")
    suspend fun searchProduct(
        @Path("siteId") siteId: String,
        @Query(ApiParameters.SEARCH_QUERY) query: String
    ): ApiSearchProduct
}