package com.realworld.io.presentation.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.realworld.io.MainActivity
import com.realworld.io.R
import com.realworld.io.databinding.FragmentDashBaordBinding
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.presentation.addarticle.LocalArticleViewModel
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

/**
 * Dashboard Article Fragment
 * Hilt view model will create entry point for Fragment
 */
@AndroidEntryPoint
class DashBoardFragment : Fragment() , ArticleAdapter.OnItemClickListener{
    private val viewModel : DashBoardViewModel by viewModels()
    private val localArticleViewModel : LocalArticleViewModel by viewModels()
    private  var _binding: FragmentDashBaordBinding?= null
    private val binding get() = _binding!!
    lateinit var articleAdapter: ArticleAdapter
    @Inject lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashBaordBinding.inflate(inflater,container,false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerview()
        bindObserver()

        /**
         * Check if internet is active or not
         * we will load data according to network state
         */

        if (requireContext().isNetworkAvailable()){
            //Fetch Remote Data
            viewModel.fetchAllArticle()
        }else {
            //Fetch Local Data
            localArticleViewModel.fetchOfflineArticle()
        }



        //Hide back button in Dashboard fragment
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.progressBar.gone()
    }

    /**
     * Logout function
     */
    private fun logout() {
        tokenManager.logout()
        findNavController().navigate(R.id.action_dashBaord_to_loginFragment)
    }


    /**
     * Initialize Recyclerview
     */
    private fun iniRecyclerview() {
        articleAdapter = ArticleAdapter(this@DashBoardFragment)
        binding.articleRcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.articleRcv.adapter = articleAdapter
        articleAdapter.filter
        articleAdapter.notifyDataSetChanged()
    }


    /**
     * Observer function
     */
    private fun bindObserver() {
        if (requireContext().isNetworkAvailable()){
            //Observe Remote data
            onlineArticleObserver()
        }else {
            //Observe Local data
            offlineArticleObserver()
        }
    }

    /**
     * Remote data observer
     * It will add data to recyclerview on success
     */
    private fun onlineArticleObserver() {
        binding.shimmerLayout.stopShimmer()
        lifecycleScope.launchWhenStarted {
            viewModel.articleUIState.collectLatest{
                when (it) {
                    is Resource.Success -> {
                        it.data?.let {
                            //passing data to function which initialize recyclerview
                            renderArticleList(it.articles)
                            binding.shimmerLayout.stopShimmer()
                            binding.shimmerLayout.gone()
                            binding.articleRcv.visible()
                        }
                    }
                    is Resource.Error -> {
                        binding.shimmerLayout.stopShimmer()
                        binding.shimmerLayout.gone()
                        binding.articleRcv.visible()
                    }
                    is Resource.Loading -> {
                        binding.shimmerLayout.startShimmer()
                    }
                }
            }
        }


    }

    /**
     * Pass Data to Recyclerview function
     */
    private fun renderArticleList(articles: MutableList<ArticleX>) {
        articleAdapter.setData(articles)
        articleAdapter.notifyDataSetChanged()
    }

    /**
     * Local data observer
     * It will add data to recyclerview on success
     */
    private fun offlineArticleObserver() {
        binding.shimmerLayout.stopShimmer()
        lifecycleScope.launchWhenCreated {
            localArticleViewModel.offlineArticleUIState.collectLatest {
                when (it) {
                    is Resource.Success -> {
                        it.data?.let {
                                //passing data to function which initialize recyclerview
                                renderArticleList(it)
                                binding.shimmerLayout.stopShimmer()
                                binding.shimmerLayout.gone()
                                binding.articleRcv.visible()
                            }
                        }
                    is Resource.Error -> {
                            binding.shimmerLayout.stopShimmer()
                            binding.shimmerLayout.gone()
                            binding.articleRcv.visible()
                        }
                    is Resource.Loading -> {
                            binding.shimmerLayout.startShimmer()
                        }
                }

            }

        }
    }

    /**
     * Open Dialog Box
     */
    private fun openDialog(article: ArticleX) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setIcon(R.drawable.demo_avatar)
            setTitle("Want to make Changes?")
            setMessage("You can Update and Delete Data!!")
            setPositiveButton("Delete") { _, _ ->
                //Confirm dialog box
                openConfirmDialogBox(article)
            }
            setNegativeButton("Update") { _, _ ->
                //Navigate to Update fragment
                navigateToUpdateFragment(article)
            }
            setNeutralButton("Cancel") { _, _ ->
            }
        }.create().show()
    }

    /**
     * Open Update Fragment
     */
    private fun navigateToUpdateFragment(article: ArticleX) {
        val action = DashBoardFragmentDirections.actionDashBaordToAddArticleFragment(false,article)
        findNavController().navigate(action)
    }

    /**
     * open Confirm DialogBox
     */
    private fun openConfirmDialogBox(article: ArticleX) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setIcon(R.drawable.demo_avatar)
            setTitle("Are You Really want to Delete it?")
            setMessage("This change cant be Revert....")
            setPositiveButton("Yes") { _, _ ->
                localArticleViewModel.deleteArticle(article)
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    /**
     * Option Search Menu initialize
     * Search function
     * Will update onCreateOptionsMenu in future because it is deprecated in java
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_info,menu)

        val searchItem = menu.findItem(R.id.actionSearch)

        val searchView: SearchView = searchItem.actionView as SearchView

        //when user will click on button
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            //Will call adapter filter
            override fun onQueryTextChange(newText: String?): Boolean {
                articleAdapter.filter.filter(newText)
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }


    /**
     * Adapter click events
     * 1. Item click
     * 2. Button Click
     * 3. Item Long Click
     */
    // 1.
    override fun itemClick(view: View, position: Int, article: ArticleX) {
        val action = DashBoardFragmentDirections.actionDashBaordToSignleArticle(article)
        findNavController().navigate(action)
    }

    // 2.
    override fun btnClick(view: View, position: Int, article: ArticleX) {
        findNavController().navigate(R.id.action_dashBaord_to_confirmFragment2)
        articleAdapter.notifyDataSetChanged()
    }

    // 3.
    override fun itemClickLong(view: View, position: Int, article: ArticleX) {
        openDialog(article)
    }


    /**
     * Option Menu initialize
     * Will update onCreateOptionsMenu in future because it is deprecated in java
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            //Add Article click handle
            R.id.add -> {
                val action = DashBoardFragmentDirections.actionDashBaordToAddArticleFragment(true,
                    ArticleX(tagList = listOf("fdsf","fdsfs"))
                )
                findNavController().navigate(action)
            }
            //User Profile click handle
            R.id.about ->{
                findNavController().navigate(R.id.action_dashBaord_to_userprofile)
            }
            //Logout click handle
            R.id.logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Delete Object for more efficient app and no memory loss
     */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}

