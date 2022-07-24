package com.mlcandidate.davidguedez.searchproduct.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.mlcandidate.davidguedez.R
import com.mlcandidate.davidguedez.common.presentation.Event
import com.mlcandidate.davidguedez.databinding.FragmentSearchProductBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchProductFragment : Fragment() {
    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchProductViewModel by viewModels()

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
        binding.productSearchProgressBar.isVisible = viewState.loading
        handleFailures(viewState.failure)
        handleNoProductFound(viewState.noProductFound)
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
                        viewModel.onEvent(SearchProductEvent.RequestSearch(query.orEmpty()))
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