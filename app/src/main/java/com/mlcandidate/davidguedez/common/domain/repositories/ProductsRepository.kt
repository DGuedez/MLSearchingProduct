package com.mlcandidate.davidguedez.common.domain.repositories

import com.mlcandidate.davidguedez.common.domain.model.product.Product

interface ProductsRepository {
    suspend fun search(siteId: String, searchQuery: String): List<Product>
}