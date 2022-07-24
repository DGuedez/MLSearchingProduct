package com.mlcandidate.davidguedez.searchproduct.presentation

import com.mlcandidate.davidguedez.common.presentation.Event
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct

data class SearchProductViewState(
    val loading: Boolean = false,
    val noProductFound: Event<String>? = null,
    val failure: Event<Throwable>? = null,
    val productResults : List<UIProduct> = emptyList()
)