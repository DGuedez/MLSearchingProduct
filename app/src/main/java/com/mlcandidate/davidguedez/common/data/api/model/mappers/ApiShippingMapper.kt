package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiShipping
import com.mlcandidate.davidguedez.common.domain.model.product.Shipping
import javax.inject.Inject

class ApiShippingMapper @Inject constructor(): ApiMapper<ApiShipping?, Shipping> {
    override fun mapToDomain(apiEntity: ApiShipping?): Shipping {
        return Shipping(
            freeShipping = apiEntity?.freeShipping ?: false
        )
    }
}