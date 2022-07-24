package com.mlcandidate.davidguedez.common.domain.model.product


sealed class Installment {
    object UNKNOWN : Installment()
    data class KNOWN(
        val quantity: Int,
        val amount: Float,
        val shipping: Shipping
    ) : Installment()
}
