package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiShipping
import com.mlcandidate.davidguedez.common.domain.model.Shipping

class ApiShippingMapper: ApiMapper<ApiShipping?, Shipping> {
    override fun mapToDomain(apiEntity: ApiShipping?): Shipping {
        return Shipping(
            freeShipping = apiEntity?.freeShipping ?: false
        )
    }
}