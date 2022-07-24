package com.mlcandidate.davidguedez.searchproduct.presentation

sealed class SearchProductEvent{
    data class RequestSearch(val query:String): SearchProductEvent()
}
