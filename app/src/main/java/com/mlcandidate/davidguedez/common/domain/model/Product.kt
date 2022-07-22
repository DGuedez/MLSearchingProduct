package com.mlcandidate.davidguedez.common.domain.model

data class Product(
    val id: String,
    val title: String,
    val price: Int,
    val thumbnail: String,
    val installments: Installment
)