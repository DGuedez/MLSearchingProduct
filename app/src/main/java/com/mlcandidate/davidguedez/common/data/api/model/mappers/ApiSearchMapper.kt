package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiProduct
import com.mlcandidate.davidguedez.common.data.api.model.ApiSearchProduct
import com.mlcandidate.davidguedez.common.domain.model.Product
import com.mlcandidate.davidguedez.common.domain.model.SearchProduct

class ApiSearchMapper(private val apiProductMapper: ApiProductMapper) : ApiMapper<ApiSearchProduct, SearchProduct> {
    override fun mapToDomain(apiEntity: ApiSearchProduct): SearchProduct {
        return SearchProduct(
            query = apiEntity.query.orEmpty(),
            results = parseProductsList(apiEntity.productResults)
        )
    }

    private fun parseProductsList(apiEntity: List<ApiProduct>?): List<Product> {
      return  apiEntity?.map { product -> apiProductMapper.mapToDomain(product)  } ?: listOf()
    }
}
