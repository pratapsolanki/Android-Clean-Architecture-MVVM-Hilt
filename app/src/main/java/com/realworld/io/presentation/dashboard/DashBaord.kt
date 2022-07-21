package com.realworld.io.presentation.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.realworld.io.MainActivity
import com.realworld.io.R
import com.realworld.io.adapter.ArticleAdapter
import com.realworld.io.databinding.FragmentDashBaordBinding
import com.realworld.io.model.ArticleModel
import com.realworld.io.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DashBaord : Fragment() , ArticleAdapter.OnItemClickListener{

    private val viewModel : ArticleViewModel by viewModels()
    private  var _binding: FragmentDashBaordBinding?= null
    private val binding get() = _binding!!
    lateinit var articleAdapter: ArticleAdapter
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
        viewModel.fetchAllArticle()
        viewModel.fetchOfflineArticle()
        bindObserver()
        iniRecyclerview()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        toobarActionHandle()
        binding.progressBar.gone()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_info,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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
        return super.onOptionsItemSelected(item)
    }


//    private fun toobarActionHandle() {
//        binding.topAppBar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.add -> {
//                    findNavController().navigate(R.id.action_dashBaord_to_addArticleFragment)
//                    true
//                }
//                R.id.about ->{
//                    findNavController().navigate(R.id.action_dashBaord_to_userprofile)
//                    true
//                }
//                R.id.logout -> {
//                    logout()
//                    true
//                }
//                else ->{
//                    false
//                }
//            }
//
//        }
//    }

    private fun logout() {
        tokenManager.logout()
//        findNavController().popBackStack(R.id.loginFragment,true)
        findNavController().navigate(R.id.loginFragment)
    }


    private fun iniRecyclerview() {
        articleAdapter = ArticleAdapter(ArrayList(),this@DashBaord)
        binding.articleRcv.layoutManager = LinearLayoutManager(requireActivity())
        binding.articleRcv.adapter = articleAdapter

    }

    private fun bindObserver() {
        binding.progressBar.gone()
        viewModel.offlineArticleList.observe(requireActivity(), Observer {
            when (it) {
                is Resource.Success -> {
                    Logger.d(it.data.toString())
                    it.data?.let {
                        articleAdapter.setData(it)
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

    override fun itemClick(view: View, position: Int, article: ArticleModel) {
        val action = DashBaordDirections.actionDashBaordToSignleArticle(article)
        findNavController().navigate(action)
    }

    override fun btnClick(view: View, position: Int, article: ArticleModel) {
        openDialog(article)
        Logger.d("$position click")
        articleAdapter.notifyDataSetChanged()
    }

    private fun openDialog(article: ArticleModel) {
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
                requireContext().toast("clicked neutral button")
            }
        }.create().show()
    }

    private fun navigateToUpdateFragment(article: ArticleModel) {
        val action =  DashBaordDirections.actionDashBaordToEditFragment(
            article
        )
        findNavController().navigate(action)
    }

    private fun openConfirmDialogBox(article: ArticleModel) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.apply {
            setIcon(R.drawable.demo_avatar)
            setTitle("Are You Really want to Delete it?")
            setMessage("This change cant be Revert....")
            setPositiveButton("Yes") { _, _ ->
                deleteArticleAction(article)
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    private fun deleteArticleAction(article: ArticleModel) {
        viewModel.deleteArticle(article)
        articleAdapter.notifyDataSetChanged()
    }

}

