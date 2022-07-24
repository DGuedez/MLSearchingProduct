package com.mlcandidate.davidguedez.common.presentation.model


data class UIProduct(
    val id: String,
    val title: String,
    val price: String,
    val thumbnail: String,
    val installmentQuantity: String,
    val InstallmentAmount: String,
    val productFreeShipping: Boolean
)