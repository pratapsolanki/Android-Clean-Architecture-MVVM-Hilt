package com.realworld.io.presentation.addarticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.realworld.io.databinding.FragmentAddArticleBinding
import com.realworld.io.model.ArticleModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddArticleFragment : Fragment() {

    private val viewModel : AddArticleViewModel by viewModels()
    private  var _binding: FragmentAddArticleBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addArticleBtn.setOnClickListener {
            val body = binding.edtBody.text.toString()
            val title = binding.edtTitle.text.toString()
            val articleModel = ArticleModel(body = "$body", title = "$title")
            viewModel.addArticle(articleModel)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}