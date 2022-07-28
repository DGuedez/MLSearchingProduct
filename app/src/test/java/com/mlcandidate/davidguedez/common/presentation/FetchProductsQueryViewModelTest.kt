@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mlcandidate.davidguedez.common.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mlcandidate.davidguedez.common.domain.model.product.Installment
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import com.mlcandidate.davidguedez.common.domain.model.product.Shipping
import com.mlcandidate.davidguedez.common.presentation.mappers.UIProductMapper
import com.mlcandidate.davidguedez.common.utils.DispatchersProvider
import com.mlcandidate.davidguedez.searchproduct.domain.RequestProductSearchUseCase
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductEvent
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductViewState
import com.google.common.truth.Truth.assertThat
import com.mlcandidate.davidguedez.common.data.SearchProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FetchProductsQueryViewModelTest {

    lateinit var viewModel: FetchProductsQueryViewModel

    val testCoroutineDispatcher = TestCoroutineDispatcher()

    val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)
    

    @Mock
    lateinit var requestProductSearchUseCase: RequestProductSearchUseCase

    @Mock
    lateinit var uiProductMapper: UIProductMapper

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var product1: Product

    private lateinit var product2: Product


    @Before
    fun setup() {

        setUpDispatcher()
        setProductValues()

        setUpViewModel()

    }



    private fun setProductValues() {
        product1 = Product(
            id = "1",
            title = "title1",
            price = 12000,
            thumbnail = "thumbnailurl",
            installments = Installment.KNOWN(quantity = 5, amount = 1.01F),
            detailsUrl = "detailUrl",
            Shipping(freeShipping = false)
        )

        product2 = Product(
            id = "2",
            title = "title2",
            price = 242000,
            thumbnail = "thumbnailurl",
            installments = Installment.KNOWN(quantity = 1, amount = 2.01F),
            detailsUrl = "detailUrl",
            Shipping(freeShipping = false)
        )
    }

    private fun setUpViewModel() {

        val dispatchersProvider = object : DispatchersProvider {
            override fun io() = Dispatchers.Main
        }

        viewModel = FetchProductsQueryViewModel(
            dispatchersProvider,
            requestProductSearchUseCase,
            uiProductMapper
        )
    }


    private fun setUpDispatcher() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun dropDown() {
        resetDispatcher()
    }


    private fun resetDispatcher() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun onSearchProductEvent_queryWithValue_remoteSearchWithProductListIsObtained() =
        testCoroutineScope.runBlockingTest {
            //Given
            val apiResult = listOf(product1, product2)
            val expectedRemoteProducts = apiResult.map { uiProductMapper.mapToView(it) }
            val query = "someQueryWithText"

            viewModel.state.observeForever { }

            val expectedViewState = SearchProductViewState(
                loading = false,
                noProductFound = null,
                failure = null,
                productResults = Event(expectedRemoteProducts)

            )

            `when`(requestProductSearchUseCase.invoke(query)).thenReturn(apiResult)

            //when
            val searchEvent = SearchProductEvent.RequestSearch(query)
            viewModel.onSearchProductEvent(searchEvent)

            // then
            verify(requestProductSearchUseCase, times(1)).invoke(query)

            val viewState = viewModel.state.value!!

            assertThat(viewState.productResults).isNotSameInstanceAs(expectedViewState.productResults)

        }
}