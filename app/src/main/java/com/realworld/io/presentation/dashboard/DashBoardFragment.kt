package com.realworld.io.presentation.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.realworld.io.MainActivity
import com.realworld.io.R
import com.realworld.io.databinding.FragmentDashBaordBinding
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashBoardFragment : Fragment() , ArticleAdapter.OnItemClickListener{

    private val viewModel : ArticleViewModel by viewModels()
    private  var _binding: FragmentDashBaordBinding?= null
    private val binding get() = _binding!!
    lateinit var articleAdapter: ArticleAdapter
    private  var flag = 0
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBaordBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireContext().isNetworkAvailable()){
            viewModel.fetchAllArticle()
            flag = 1
        }else {
            flag = 0
            viewModel.fetchOfflineArticle()
        }

        bindObserver()
        iniRecyclerview()
        if (requireContext().isNetworkAvailable()){
            onlineArticleObserver()
        }else {
            offlineArticleObserver()
        }
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.progressBar.gone()
    }





    private fun logout() {
        tokenManager.logout()
//        findNavController().popBackStack()
        findNavController().navigate(R.id.action_dashBaord_to_loginFragment)
//        findNavController().navigate(R.id.loginFragment)
    }


    private fun iniRecyclerview() {
        articleAdapter = ArticleAdapter(this@DashBoardFragment)
        binding.articleRcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.articleRcv.adapter = articleAdapter
        articleAdapter.filter
        articleAdapter.notifyDataSetChanged()
    }


    private fun bindObserver() {
        binding.progressBar.gone()
    }

    private fun onlineArticleObserver() {
        binding.shimmerLayout.stopShimmer()

        viewModel.articleList.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    Logger.d(it.data.toString())
                    it.data?.let {
                        renderPhotosList(it.articles)
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.gone()
                        binding.articleRcv.visible()
                    }
                }
                is Resource.Error -> {
                    binding.shimmerLayout.stopShimmer();
                    binding.shimmerLayout.gone()
                    binding.articleRcv.visible()
                    Logger.d(it.errorMessage.toString() + "Error")
                }
                is Resource.Loading -> {
                    binding.shimmerLayout.startShimmer();
                }
            }
        })
    }

    private fun renderPhotosList(articles: MutableList<ArticleX>) {
        articleAdapter.setData(articles)
        articleAdapter.notifyDataSetChanged()
    }

    private fun offlineArticleObserver() {
        viewModel.offlineArticleList.observe(viewLifecycleOwner, Observer {
            binding.shimmerLayout.stopShimmer();
            binding.shimmerLayout.gone()
            binding.articleRcv.visible()
            articleAdapter.setData(it)
        })
    }

    override fun itemClick(view: View, position: Int, article: ArticleX) {
        val action = DashBoardFragmentDirections.actionDashBaordToSignleArticle(article)
        findNavController().navigate(action)
    }

    override fun btnClick(view: View, position: Int, article: ArticleX) {
        findNavController().navigate(R.id.action_dashBaord_to_confirmFragment2)
        articleAdapter.notifyDataSetChanged()
    }

    override fun itemClickLong(view: View, position: Int, article: ArticleX) {
        if (flag == 0){
           openDialog(article)
        }
    }

    private fun openDialog(article: ArticleX) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setIcon(R.drawable.demo_avatar)
            setTitle("Want to make Changes?")
            setMessage("You can Update and Delete Data!!")
            setPositiveButton("Delete") { _, _ ->
                openConfirmDialogBox(article)
            }
            setNegativeButton("Update") { _, _ ->
                navigateToUpdateFragment(article)
            }
            setNeutralButton("Cancel") { _, _ ->
            }
        }.create().show()
    }

    private fun navigateToUpdateFragment(article: ArticleX) {
        val action = DashBoardFragmentDirections.actionDashBaordToEditFragment(article)
        findNavController().navigate(action)
    }

    private fun openConfirmDialogBox(article: ArticleX) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setIcon(R.drawable.demo_avatar)
            setTitle("Are You Really want to Delete it?")
            setMessage("This change cant be Revert....")
            setPositiveButton("Yes") { _, _ ->
               viewModel.deleteArticle(article)
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_info,menu)

        val searchItem = menu.findItem(R.id.actionSearch)

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                articleAdapter.filter.filter(newText)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.add -> {
                findNavController().navigate(R.id.action_dashBaord_to_addArticleFragment)
            }
            R.id.about ->{
                findNavController().navigate(R.id.action_dashBaord_to_userprofile)
            }
            R.id.logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}

