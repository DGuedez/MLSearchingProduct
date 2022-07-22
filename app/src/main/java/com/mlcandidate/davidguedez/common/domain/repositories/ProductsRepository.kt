package com.mlcandidate.davidguedez.common.domain.repositories

import com.mlcandidate.davidguedez.common.domain.model.SearchProduct

interface ProductsRepository {
    suspend fun search(siteId: String): SearchProduct
}