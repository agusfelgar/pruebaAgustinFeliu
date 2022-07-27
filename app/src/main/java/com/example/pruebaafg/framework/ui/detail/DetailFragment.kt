package com.example.pruebaafg.framework.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pruebaafg.R
import com.example.pruebaafg.data.model.Article
import com.example.pruebaafg.databinding.FragmentDetailBinding
import com.example.pruebaafg.framework.ui.detail.DetailFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    private lateinit var article: Article

    val args : DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        article = args.article
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = args.article.title
        viewModel.onInit(article)

        viewModel.articleUrl.observe(viewLifecycleOwner, Observer {urlString ->

            binding.webviewArticle.webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    binding.webviewArticle.visibility = View.GONE
                    val builder = AlertDialog.Builder(binding.webviewArticle.context)
                    builder.setTitle(getString(R.string.error_title))
                        .setMessage(getString(R.string.tv_error))
                        .setCancelable(false)
                        .setPositiveButton(R.string.btn_back){dialogInterface, it ->
                            dialogInterface.dismiss()
                            findNavController().popBackStack()
                        }
                        .create()
                        .show()
                }
            }
            binding.webviewArticle.loadUrl(urlString)
        })

    }
}