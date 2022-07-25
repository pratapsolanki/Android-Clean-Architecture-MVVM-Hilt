package com.realworld.io.presentation.addarticle

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.realworld.io.R
import com.realworld.io.databinding.FragmentAddArticleBinding
import com.realworld.io.domain.model.Article
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.Author
import com.realworld.io.util.TokenManager
import com.realworld.io.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AddArticleFragment : Fragment() {

    private val viewModel : AddArticleViewModel by viewModels()
    private  var _binding: FragmentAddArticleBinding?= null
    private val binding get() = _binding!!
    @Inject
    lateinit var  tokenManager: TokenManager
    var selectedText = ""

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

        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autocompleteTV.setAdapter(arrayAdapter)

        binding.autocompleteTV.setOnItemClickListener { adapterView, view, position, l ->
            selectedText = languages.get(position)
        }

        binding.addArticleBtn.setOnClickListener {
            val validationResult = checkValidation()
            if(validationResult){
                val body = binding.edtBody.text.toString().trim()
                val title = binding.edtTitle.text.toString().trim()
                val desc = binding.edtDesc.text.toString().trim()
                val array = mutableListOf<String>()
                val username = tokenManager.getName()
                array.add(selectedText)
                val articleModel = ArticleX(body = body, title = title , description = desc, tagList = array, createdAt = Date().toString(), author = Author("",false, image = "", username = username))
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

        if(selectedText.isBlank()){
            requireContext().toast("Please Select Tag")
            returnValue = false
        }
        return returnValue
    }
}