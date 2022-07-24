package com.mlcandidate.davidguedez.common.data.api.model.mappers

import com.mlcandidate.davidguedez.common.data.api.model.ApiInstallment
import com.mlcandidate.davidguedez.common.domain.model.product.Installment
import javax.inject.Inject

class ApiInstallmentsMapper @Inject constructor(private val apiShippingMapper: ApiShippingMapper) :
    ApiMapper<ApiInstallment?, Installment> {
    override fun mapToDomain(apiEntity: ApiInstallment?): Installment {
        return if (apiEntity != null) {
            Installment.KNOWN(
                quantity = apiEntity.quantity
                    ?: throw MappingException("Installment Quantity can't be null"),
                amount = apiEntity.amount
                    ?: throw MappingException("Installment Amount can't be null"),
                shipping = apiShippingMapper.mapToDomain(apiEntity.shipping)
            )
        } else {
            Installment.UNKNOWN
        }
    }
}