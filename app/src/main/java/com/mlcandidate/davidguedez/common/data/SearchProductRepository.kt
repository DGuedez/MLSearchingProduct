package com.mlcandidate.davidguedez.common.data

import com.mlcandidate.davidguedez.common.data.api.APIProductService
import com.mlcandidate.davidguedez.common.data.api.model.mappers.ApiSearchMapper
import com.mlcandidate.davidguedez.common.domain.model.NetworkException
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import com.mlcandidate.davidguedez.common.domain.repositories.ProductsRepository
import retrofit2.HttpException
import javax.inject.Inject

class SearchProductRepository @Inject constructor(
    private val apiProductService: APIProductService,
    private val apiSearchMapper: ApiSearchMapper
) : ProductsRepository {
    override suspend fun search(searchQuery: String): List<Product> {
        try {
            val searchResult = apiSearchMapper.mapToDomain(apiProductService.searchProduct(query= searchQuery))
            return searchResult.results
        } catch (exception: HttpException) {
            throw NetworkException(exception.message() ?: "Code${exception.code()}")
        }
    }

}