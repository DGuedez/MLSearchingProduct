@file:OptIn(ExperimentalCoroutinesApi::class)

package com.mlcandidate.davidguedez.common.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mlcandidate.davidguedez.common.data.SearchProductRepository
import com.mlcandidate.davidguedez.common.domain.model.NetworkException
import com.mlcandidate.davidguedez.common.domain.model.product.Installment
import com.mlcandidate.davidguedez.common.domain.model.product.Product
import com.mlcandidate.davidguedez.common.domain.model.product.Shipping
import com.mlcandidate.davidguedez.common.presentation.mappers.UIProductMapper
import com.mlcandidate.davidguedez.common.utils.DispatchersProvider
import com.mlcandidate.davidguedez.searchproduct.domain.RequestProductSearchUseCase
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductEvent
import com.mlcandidate.davidguedez.searchproduct.presentation.SearchProductViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class FetchProductsQueryViewModelTest {

    lateinit var viewModel: FetchProductsQueryViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @MockK
    lateinit var repository : SearchProductRepository

    lateinit var requestProductSearchUseCase: RequestProductSearchUseCase

    lateinit var uiProductMapper: UIProductMapper

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var product1: Product

    private lateinit var product2: Product


    @Before
    fun setup() {
        MockKAnnotations.init(this)
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
        uiProductMapper = UIProductMapper()

        requestProductSearchUseCase = spyk(RequestProductSearchUseCase(repository))

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

            val expectedViewState = SearchProductViewState(
                loading = false,
                noProductFound = null,
                failure = null,
                productResults = Event(expectedRemoteProducts)

            )
            val expectedStateList = expectedViewState.productResults?.getContentIfNotHandled()
            coEvery{repository.search(query)} returns apiResult

            //when
            val searchEvent = SearchProductEvent.RequestSearch(query)
            viewModel.onSearchProductEvent(searchEvent)

            // then
            coVerify(exactly = 1){requestProductSearchUseCase.invoke(query)}

            val viewState = viewModel.state.value!!
            val viewStateListResult = viewState.productResults?.getContentIfNotHandled()

            assertThat(viewStateListResult).isEqualTo(expectedStateList)

        }

    @Test
    fun onSearchProductEvent_queryWithValue_networkExceptionIsThrown()  =  testCoroutineScope.runBlockingTest{
        //Given
        val failureMessage = "messageFailure"
        val expectedFailure = NetworkException(failureMessage)
        val query = "someQueryWithText"

        coEvery { repository.search(query) } throws expectedFailure

        //when
        val searchEvent = SearchProductEvent.RequestSearch(query)
        viewModel.onSearchProductEvent(searchEvent)

        // then
        coVerify{requestProductSearchUseCase.invoke(query)}

        val viewState = viewModel.state.value!!
        val viewStateFailureResult = viewState.failure?.getContentIfNotHandled()

        assertThat(viewStateFailureResult).isInstanceOf(NetworkException::class.java)
    }


    @Test
    fun getFoundProductList_noRemoteServiceCalled_productListIsNull() =
        testCoroutineScope.runBlockingTest {

            //when
            val result = viewModel.getFoundProductList()

            //then
            coVerify { requestProductSearchUseCase wasNot called }
            assertThat(result).isNull()
        }

    @Test
    fun getFoundProductList_successServiceCall_productListIsObtained() =
        testCoroutineScope.runBlockingTest {
            //Given
            val apiResult = listOf(product1, product2)
            val expectedRemoteProducts = apiResult.map { uiProductMapper.mapToView(it) }
            val query = "someQueryWithText"

            viewModel.state.observeForever { }

            coEvery {requestProductSearchUseCase.invoke(query)} returns apiResult

            //when
            val searchEvent = SearchProductEvent.RequestSearch(query)
            viewModel.onSearchProductEvent(searchEvent)

            val result = viewModel.getFoundProductList()

            // then
            coVerify{requestProductSearchUseCase.invoke(any())}

            assertThat(result).isEqualTo(expectedRemoteProducts)

        }
}