package com.mlcandidate.davidguedez.searchproduct.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.mlcandidate.davidguedez.R
import com.mlcandidate.davidguedez.common.presentation.Event
import com.mlcandidate.davidguedez.common.presentation.FetchProductsQueryViewModel
import com.mlcandidate.davidguedez.databinding.FragmentSearchProductBinding
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchProductFragment : Fragment() {
    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FetchProductsQueryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        setUpSearchViewListener()
        observeViewStateUpdates()
    }

    private fun observeViewStateUpdates() {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it)
        }
    }

    private fun updateScreenState(viewState: SearchProductViewState) {
        val (loading, notProductFound, failure, productResults) = viewState
        binding.productSearchProgressBar.isVisible = loading
        handleFailures(failure)
        handleNoProductFound(notProductFound)
        handleProductResult(productResults)
    }

    private fun handleProductResult(productResults: List<UIProduct>) {
        if (productResults.isNotEmpty()) {
            goToProductsListSection()
        }
    }

    private fun goToProductsListSection() {
        findNavController().navigate(R.id.action_mainFragment_to_foundProductsResult)
    }

    private fun handleNoProductFound(noProductFoundEvent: Event<String>?) {
        noProductFoundEvent?.getContentIfNotHandled().also {
            showNotFoundProductPrompt()
        } ?: hideNotFoundProductPrompt()
    }

    private fun showNotFoundProductPrompt() {
        binding.productSearchNotFound.isVisible = true
    }

    private fun hideNotFoundProductPrompt() {
        binding.productSearchNotFound.isVisible = false
    }


    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return
        val defaultFailureMessage = getString(R.string.default_failure_message)
        val snackBarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            defaultFailureMessage
        } else {
            unhandledFailure.message!!
        }
        Snackbar.make(requireView(), snackBarMessage, Snackbar.LENGTH_SHORT).show()
    }


    private fun setUpSearchViewListener() {
        binding.productSearchView.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.onSearchProductEvent(SearchProductEvent.RequestSearch(query.orEmpty()))
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}