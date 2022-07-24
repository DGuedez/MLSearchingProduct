package com.mlcandidate.davidguedez.searchproduct.domain

import com.mlcandidate.davidguedez.common.data.SearchProductRepository
import com.mlcandidate.davidguedez.common.data.api.ApiParameters
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import javax.inject.Inject


class RequestProductSearchUseCase @Inject constructor(private val searchProductRepository: SearchProductRepository) {
    suspend operator fun invoke(query: String): List<Product> =
        searchProductRepository.search(ApiParameters.SITE_ID_MLC, query)
}