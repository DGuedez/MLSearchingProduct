package com.mlcandidate.davidguedez.foundproducts.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mlcandidate.davidguedez.databinding.FragmentFoundProductsResultBinding
import com.mlcandidate.davidguedez.common.presentation.model.UIProduct
import com.mlcandidate.davidguedez.common.presentation.FetchProductsQueryViewModel
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

    private fun getFoundProductsList() : List<UIProduct>? = viewModel.getFoundProductList()

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
        getFoundProductsList()?.let { list ->  foundProductAdapter.submitList(list) }
    }

    private fun createAdapter(): FoundProductsAdapter = FoundProductsAdapter()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}