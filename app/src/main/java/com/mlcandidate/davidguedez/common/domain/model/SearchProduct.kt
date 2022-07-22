package com.mlcandidate.davidguedez.common.domain.model

import com.mlcandidate.davidguedez.common.domain.model.Product

data class SearchProduct(
    val query: String,
    val results: List<Product>
)