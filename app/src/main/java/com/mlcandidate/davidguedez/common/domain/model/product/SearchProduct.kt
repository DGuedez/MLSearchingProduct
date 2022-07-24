package com.mlcandidate.davidguedez.common.domain.model.product

data class SearchProduct(
    val query: String,
    val results: List<Product>
)