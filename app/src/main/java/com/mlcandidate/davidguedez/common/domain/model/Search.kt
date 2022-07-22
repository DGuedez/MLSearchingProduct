package com.mlcandidate.davidguedez.common.domain.model

import com.mlcandidate.davidguedez.common.domain.model.Product

data class Search(
    val query: String,
    val results: List<Product>
)