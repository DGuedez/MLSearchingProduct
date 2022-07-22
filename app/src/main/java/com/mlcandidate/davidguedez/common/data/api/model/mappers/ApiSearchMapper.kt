package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiProduct
import com.mlcandidate.davidguedez.common.data.api.model.ApiSearch
import com.mlcandidate.davidguedez.common.domain.model.Product
import com.mlcandidate.davidguedez.common.domain.model.Search

class ApiSearchMapper(private val apiProductMapper: ApiProductMapper) : ApiMapper<ApiSearch, Search> {
    override fun mapToDomain(apiEntity: ApiSearch): Search {
        return Search(
            query = apiEntity.query.orEmpty(),
            results = parseProductsList(apiEntity.productResults)
        )
    }

    private fun parseProductsList(apiEntity: List<ApiProduct>?): List<Product> {
      return  apiEntity?.map { product -> apiProductMapper.mapToDomain(product)  } ?: listOf()
    }
}
