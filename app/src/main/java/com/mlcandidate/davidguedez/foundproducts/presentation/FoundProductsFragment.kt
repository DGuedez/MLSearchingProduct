package com.mlcandidate.davidguedez.foundproducts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mlcandidate.davidguedez.R
import com.mlcandidate.davidguedez.common.presentation.FetchProductsQueryViewModel
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import com.mlcandidate.davidguedez.databinding.FragmentFoundProductsResultBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoundProductsFragment : Fragment() {
    private var _binding: FragmentFoundProductsResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FetchProductsQueryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoundProductsResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun getFoundProductsList(): List<UIProduct>? = viewModel.getFoundProductList()

    private fun setUpUi() {
        val adapter = createAdapter()
        setUpRecyclerView(adapter)
    }

    private fun setUpRecyclerView(foundProductAdapter: FoundProductsAdapter) {
        populateAdapter(foundProductAdapter)
        binding.foundProductRecyclerView.apply {
            adapter = foundProductAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun populateAdapter(foundProductAdapter: FoundProductsAdapter) {
        getFoundProductsList()?.let { list -> foundProductAdapter.submitList(list) }
    }

    private fun createAdapter(): FoundProductsAdapter = FoundProductsAdapter(::clickOnFoundProduct)

    private fun clickOnFoundProduct(detailUrl: String) {
        if (detailUrl.isEmpty()) {
            showNoProductDetailPrompt()
        } else {
            goToProductDetail(detailUrl)
        }
    }

    private fun goToProductDetail(detailUrl: String) {
        val action =
            FoundProductsFragmentDirections.actionFoundProductsResultToDetailsProductFragment()
                .setDeatilsUrl(detailUrl)
        findNavController().navigate(action)
    }

    private fun showNoProductDetailPrompt() {
        Snackbar.make(
            requireView(),
            getString(R.string.found_product_has_not_details),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}