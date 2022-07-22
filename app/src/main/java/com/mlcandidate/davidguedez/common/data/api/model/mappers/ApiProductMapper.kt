package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiProduct
import com.mlcandidate.davidguedez.common.domain.model.Product

class ApiProductMapper(private val apiInstallmentsMapper: ApiInstallmentsMapper): ApiMapper<ApiProduct?,Product> {
    override fun mapToDomain(apiEntity: ApiProduct?): Product {
        return Product(
            id = apiEntity?.id ?: throw MappingException("Product ID can't be null"),
            title = apiEntity.title.orEmpty(),
            price = apiEntity.price?: throw MappingException("Product Price can't be null"),
            thumbnail = apiEntity.thumbnail.orEmpty(),
            installments = apiInstallmentsMapper.mapToDomain(apiEntity.installments)
        )
    }




}