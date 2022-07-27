package com.example.pruebaafg.framework.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pruebaafg.R
import com.example.pruebaafg.data.model.Article
import com.example.pruebaafg.data.model.PeriodEnum
import com.example.pruebaafg.data.model.TypeEnum
import com.example.pruebaafg.databinding.FragmentListBinding
import com.example.pruebaafg.framework.ui.list.ListFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private val viewModel by viewModels<ListViewModel>()

    //Parameters
    private var facebook: Boolean = false
    private var twitter: Boolean = false
    private var type: TypeEnum = TypeEnum.NONE
    private var period: PeriodEnum = PeriodEnum.NONE

    val args: ListFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title = getString(R.string.app_name)

        //Get Arguments
        facebook = args.facebook
        twitter = args.twitter
        type = args.type
        period = args.period

        //Init ViewModel
        viewModel.onInit(type, period, facebook, twitter)

        //Define observers
        viewModel.loadingVisibility.observe(viewLifecycleOwner, Observer { visible ->
            if (visible) {
                binding.layoutLoader.root.visibility = View.VISIBLE
                binding.rvArticles.visibility = View.GONE
            } else {
                binding.layoutLoader.root.visibility = View.GONE
                binding.rvArticles.visibility = View.VISIBLE
            }
        })
        viewModel.articleList.observe(viewLifecycleOwner, Observer { list ->
            binding.rvArticles.adapter = ArticleListAdapter(list) { article ->
                onArticleClicked(
                    article
                )
            }
        })
        viewModel.errorVisibility.observe(viewLifecycleOwner, Observer { visible ->
            if (visible) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle(getString(R.string.error_title))
                    .setMessage(getString(R.string.tv_error_list))
                    .setCancelable(false)
                    .setPositiveButton(R.string.btn_back){dialogInterface, it ->
                        dialogInterface.dismiss()
                        findNavController().popBackStack()
                    }
                    .create()
                    .show()
            }
        })

        viewModel.noResultVisibility.observe(viewLifecycleOwner, Observer { visible ->
            if(visible) {
                binding.layoutLoader.progressBarLoader.visibility = View.GONE
                binding.layoutLoader.progressBarLoader1.visibility = View.GONE
                binding.layoutLoader.btnBack.visibility = View.VISIBLE
                binding.layoutLoader.tvLoading.text = getString(R.string.tv_no_items)
            }
        })

        binding.layoutLoader.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun onArticleClicked(article : Article) {
        val dir = ListFragmentDirections.actionListFragmentToDetailFragment(article)
        findNavController().navigate(dir)
    }



}