package com.mlcandidate.davidguedez.common.data

import com.mlcandidate.davidguedez.common.data.api.APIProductService
import com.mlcandidate.davidguedez.common.domain.model.SearchProduct
import com.mlcandidate.davidguedez.common.domain.repositories.ProductsRepository

class SearchProductRepository(private val apiProductService: APIProductService) : ProductsRepository{
    override suspend fun search(siteId: String): SearchProduct = apiProductService.searchProduct(siteId)

}