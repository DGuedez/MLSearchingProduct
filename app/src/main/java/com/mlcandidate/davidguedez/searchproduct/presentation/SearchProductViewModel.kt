package com.mlcandidate.davidguedez.searchproduct.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import com.mlcandidate.davidguedez.common.presentation.Event
import com.mlcandidate.davidguedez.common.utils.DispatchersProvider
import com.mlcandidate.davidguedez.common.utils.createExceptionHandler
import com.mlcandidate.davidguedez.searchproduct.domain.RequestProductSearchUseCase
import com.mlcandidate.davidguedez.searchproduct.presentation.model.EmptyQueryException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchProductViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val requestProductSearch: RequestProductSearchUseCase
) : ViewModel() {

    val state: LiveData<SearchProductViewState> get() = _state


    private val _state = MutableLiveData<SearchProductViewState>()

    init {
        _state.value = SearchProductViewState()
    }

    fun onEvent(event: SearchProductEvent) {
        when (event) {
            is SearchProductEvent.RequestSearch -> handleSearchQuery(event.query)
        }
    }

    private fun handleSearchQuery(query: String) {
        if (query.isEmpty()) {
            _state.value = state.value!!.copy(loading = false)
        } else {
            requestSearch(query)
        }
    }

    private fun requestSearch(query: String) {
        val exceptionHandler = viewModelScope.createExceptionHandler { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            generateLoadingState()
            val productSearchResult = withContext(dispatchersProvider.io()) {
                requestProductSearch(query)
            }
            generateProductListContentState(productSearchResult)
        }
    }

    private fun generateProductListContentState(productSearchResult: List<Product>) {
        _state.value = state.value!!.copy(loading = false, noProductFound = null, failure = null)
        Log.d("viewmodel-product", productSearchResult.first().title)
    }


    private fun generateLoadingState() {
        _state.value = state.value!!.copy(loading = true, noProductFound = null, failure = null)
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is EmptyQueryException -> updateEmptyQueryFailure()

        }
    }

    private fun updateEmptyQueryFailure() {
        _state.value = state.value!!.copy(
            loading = false, noProductFound = null, failure = Event(EmptyQueryException())
        )
    }


}