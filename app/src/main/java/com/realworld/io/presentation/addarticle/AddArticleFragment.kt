package com.realworld.io.presentation.addarticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
            val validationResult = checkValidation()
            if(validationResult){
                val body = binding.edtBody.text.toString().trim()
                val title = binding.edtTitle.text.toString().trim()
                val desc = binding.edtDesc.text.toString().trim()
                val articleModel = ArticleModel(body = body, title = title , description = desc)
                viewModel.addArticle(articleModel)
                findNavController().popBackStack()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun checkValidation(): Boolean {
        var returnValue = true
        if (binding.edtBody.text.toString().trim().isBlank()) {
            binding.inputBody.error = "Short Description can't be Empty"
            returnValue = false
        } else binding.inputBody.error = null

        if (binding.edtTitle.text.toString().trim().isBlank()) {
            binding.inputTitle.error = "Title can't be Empty"
            returnValue = false
        } else binding.inputTitle.error = null

        if (binding.edtDesc.text.toString().trim().isBlank()) {
            binding.inputDesc.error = "Description can't be Empty"
            returnValue = false
        } else binding.inputDesc.error = null

        return returnValue
    }
}