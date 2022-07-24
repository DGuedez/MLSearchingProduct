package com.mlcandidate.davidguedez.common.presentation.mappers

import com.mlcandidate.davidguedez.common.domain.model.product.Installment
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import javax.inject.Inject

class UIProductMapper @Inject constructor() : UIMapper<Product, UIProduct> {
    override fun mapToView(input: Product): UIProduct {
        return UIProduct(
            id = input.id,
            title = input.title,
            price = input.price.toString(),
            thumbnail = input.thumbnail,
            installmentQuantity = getInstallmentQuantity(input),
            InstallmentAmount = getInstallmentAmount(input),
            productFreeShipping = getShippingInfo(input.installments)
        )
    }

    private fun getShippingInfo(installments: Installment): Boolean {
        return installments is Installment.KNOWN && installments.shipping.freeShipping
    }

    private fun getInstallmentAmount(input: Product): String {
        return when (val installment = input.installments) {
            is Installment.KNOWN -> installment.amount.toString()
            Installment.UNKNOWN -> ""
        }
    }

    private fun getInstallmentQuantity(input: Product): String {
        return when (val installment = input.installments) {
            is Installment.KNOWN -> installment.quantity.toString()
            Installment.UNKNOWN -> ""
        }
    }
}