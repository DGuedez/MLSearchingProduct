package com.mlcandidate.davidguedez.detailsproduct.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mlcandidate.davidguedez.databinding.FragmentDetailsProductBinding

class DetailsProductFragment : Fragment() {


    private var _binding: FragmentDetailsProductBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpWebView()
    }

    private fun setUpWebView() {
        val detailsUrl = args.deatilsUrl
        if (detailsUrl.isNotEmpty()) {
            binding.detailsProductWebview.apply {
                settings.javaScriptEnabled = true
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        super.onProgressChanged(view, newProgress)
                        binding.detailsProductProgressbar.apply {
                            progress = 0
                            visibility = View.VISIBLE
                            incrementProgressBy(newProgress)
                        }
                        if (newProgress == 100) {
                            binding.detailsProductProgressbar.visibility = View.GONE
                        }
                    }
                }
                loadUrl(detailsUrl)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}