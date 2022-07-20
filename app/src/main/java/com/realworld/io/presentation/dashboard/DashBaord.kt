package com.realworld.io.presentation.dashboard

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.realworld.io.R
import com.realworld.io.adapter.ArticleAdapter
import com.realworld.io.databinding.FragmentDashBaordBinding
import com.realworld.io.model.ArticleModel
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBaord : Fragment() {

    private val viewModel : ArticleViewModel by viewModels()
    private  var _binding: FragmentDashBaordBinding?= null
    private val binding get() = _binding!!
    @Inject lateinit var articleAdapter: ArticleAdapter
    @Inject lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBaordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchAllArticle()
        viewModel.fetchOfflineArticle()
        bindObserver()
        iniRecyclerview()
        toobarActionHandle()
        binding.progressBar.gone()
    }

    private fun openOptionmenu(position: Int) {
        val popupMenu = PopupMenu(requireActivity() , binding.articleRcv[position].findViewById(R.id.topAppBar))
        popupMenu.inflate(R.menu.article_option_menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.update -> {
                    true
                }
                R.id.delete ->{
                    true
                }
                else ->{
                    false
                }
            }
        }
        popupMenu.show()

    }

    private fun toobarActionHandle() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add -> {
                    findNavController().navigate(R.id.action_dashBaord_to_addArticleFragment)
                    true
                }
                R.id.about ->{
                    findNavController().navigate(R.id.action_dashBaord_to_userprofile)
                    true
                }
                R.id.logout -> {
                    logout()
                    true
                }
                else ->{
                    false
                }
            }

        }
    }

    private fun logout() {
        tokenManager.logout()
//        findNavController().popBackStack(R.id.loginFragment,true)
        findNavController().navigate(R.id.loginFragment)
    }


    private fun iniRecyclerview() {
        binding.articleRcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.articleRcv.adapter = articleAdapter
        articleAdapter.registerInterface(object : ArticleAdapter.OnClickItemListerner {
            override fun item(position: Int, model: ArticleModel) {
                openOptionmenu(position)
                Logger.d("$position")
            }

        })
    }

    private fun bindObserver() {
        binding.progressBar.gone()
//        viewModel.articleList.observe(requireActivity(), Observer {
//            when (it) {
//                is Resource.Success -> {
//                    Logger.d(it.data.toString())
//                    it.data?.let {
//                        articleAdapter.setArticle(it.articles)
//                        binding.progressBar.gone()
//                    }
//                }
//                is Resource.Error -> {
//                    binding.progressBar.gone()
//                    Logger.d(it.errorMessage.toString() + "Error")
//                }
//                is Resource.Loading -> {
//                    binding.progressBar.visible()
//                }
//            }
//        })

        viewModel.offlineArticleList.observe(requireActivity(), Observer {
            when (it) {
                is Resource.Success -> {
                    Logger.d(it.data.toString())
                    it.data?.let {
                        articleAdapter.setArticle(it)
                        binding.progressBar.gone()
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    Logger.d(it.errorMessage.toString() + "Error")
                }
                is Resource.Loading -> {
                    binding.progressBar.visible()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

