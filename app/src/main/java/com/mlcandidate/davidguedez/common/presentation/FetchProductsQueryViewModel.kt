package com.mlcandidate.davidguedez.common.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlcandidate.davidguedez.common.domain.model.NetworkException
import com.mlcandidate.davidguedez.common.domain.model.NetworkUnavailableException
import com.mlcandidate.davidguedez.common.presentation.mappers.UIProductMapper
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import com.mlcandidate.davidguedez.common.utils.DispatchersProvider
import com.mlcandidate.davidguedez.common.utils.createExceptionHandler
import com.mlcandidate.davidguedez.searchproduct.domain.RequestProductSearchUseCase
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductEvent
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FetchProductsQueryViewModel @Inject constructor(
    private val dispatchersProvider: DispatchersProvider,
    private val requestProductSearch: RequestProductSearchUseCase,
    private val uiProductMapper: UIProductMapper
) : ViewModel() {

    val state: LiveData<SearchProductViewState> get() = _state
    private var foundProductsResult: List<UIProduct>? = null

    private val _state = MutableLiveData<SearchProductViewState>()

    init {
        _state.value = SearchProductViewState()
    }

    fun onSearchProductEvent(event: SearchProductEvent) {
        when (event) {
            is SearchProductEvent.RequestSearch -> handleSearchQuery(event.query)
        }
    }

    fun getFoundProductList() = foundProductsResult

    private fun handleSearchQuery(query: String) {
        requestSearch(query)
    }

    private fun requestSearch(query: String) {
        val exceptionHandler = viewModelScope.createExceptionHandler { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            generateLoadingState()
            val productSearchResult = withContext(dispatchersProvider.io()) {
                requestProductSearch(query)
            }
            foundProductsResult =
                productSearchResult.map { uiProductMapper.mapToView(it) }.also { list ->
                    generateProductListContentState(list)
                }

        }
    }

    private fun generateProductListContentState(productSearchResult: List<UIProduct>) {
        _state.value = state.value!!.copy(
            loading = false,
            noProductFound = null,
            failure = null,
            productResults = Event(productSearchResult)
        )
    }


    private fun generateLoadingState() {
        _state.value = state.value!!.copy(loading = true, noProductFound = null, failure = null)
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                handleNetworkFailure(failure)
            }

        }
    }

    private fun handleNetworkFailure(failure: Throwable) {
        _state.value = state.value!!.copy(
            loading = false,
            noProductFound = null,
            failure = Event(failure),
            productResults = null
        )
    }

}