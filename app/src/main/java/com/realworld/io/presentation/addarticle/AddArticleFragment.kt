package com.realworld.io.presentation.addarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.realworld.io.R
import com.realworld.io.databinding.FragmentAddArticleBinding
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.Author
import com.realworld.io.presentation.dashboard.ArticleAdapter
import com.realworld.io.util.TokenManager
import com.realworld.io.util.isEmptyString
import com.realworld.io.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * Add OR Edit Article Fragment
 */
@AndroidEntryPoint
class AddArticleFragment : Fragment() {

    private val viewModel : LocalArticleViewModel by viewModels()
    private  var _binding: FragmentAddArticleBinding?= null
    private val binding get() = _binding!!
    private val args: AddArticleFragmentArgs by navArgs()

    @Inject
    lateinit var  tokenManager: TokenManager
    private var selectedText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //Check the argument if add or update article
        if (args.isFrom){
            addArticle()
        }else{
            editArticle()
        }


        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Edit Article Function
     */
    private fun editArticle() {
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autocompleteTV.setAdapter(arrayAdapter)
        binding.addArticleBtn.text = "Update Article"

        binding.apply {
            args.sampleData.let {
                edtBody.setText(it.body)
                edtTitle.setText(it.title)
                edtDesc.setText(it.description)
                autocompleteTV.setText(it.tagList[0], false)
            }

            addArticleBtn.setOnClickListener {
                val body = edtBody.text.toString().trim()
                val title = edtTitle.text.toString().trim()
                val desc = edtDesc.text.toString().trim()
                val category = mutableListOf<String>()
                val createDate = args.sampleData.createdAt.toString()
                val username = tokenManager.getName()
                category.add(autocompleteTV.text.toString().trim())

                val articleModel = ArticleX(
                    body = body,
                    title = title,
                    createdAt = createDate,
                    description = desc,
                    tagList = category,
                    updatedAt = Date().toString(),
                    author = Author("", false, "", username)
                )
                viewModel.updateArticle(articleModel)
                validation()
                if ( validation()){
                    findNavController().popBackStack()
                }

            }
        }
    }

    /**
     * Add Article Function
     */
    private fun addArticle() {
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autocompleteTV.setAdapter(arrayAdapter)
        binding.addArticleBtn.text = "Publish Article"
        binding.autocompleteTV.setOnItemClickListener { _, _, position, _ ->
            selectedText = languages[position]
        }

        binding.addArticleBtn.setOnClickListener {

                val body = binding.edtBody.text.toString().trim()
                val title = binding.edtTitle.text.toString().trim()
                val desc = binding.edtDesc.text.toString().trim()
                val array = mutableListOf<String>()
                val username = tokenManager.getName()
                array.add(selectedText)
                val articleModel = ArticleX(
                    body = body,
                    title = title,
                    description = desc,
                    tagList = array,
                    createdAt = Date().toString(),
                    author = Author("", false, image = "", username = username)
                )
            viewModel.addArticle(articleModel)
            if ( validation()){
                findNavController().popBackStack()
            }
        }
    }

    /**
     * Validation Form Function
     */
    private fun validation()  :Boolean{
            viewModel.state.title = binding.edtTitle.text.toString()
            binding.inputTitle.error = viewModel.state.titleError

            viewModel.state.shortDesc = binding.edtBody.text.toString()
            binding.inputBody.error = viewModel.state.shortDescError

            viewModel.state.description = binding.edtDesc.text.toString()
            binding.inputDesc.error = viewModel.state.descriptionError

        return binding.inputTitle.error.isNullOrEmpty()
                && binding.inputBody.error.isNullOrEmpty()
                && binding.inputDesc.error.isNullOrEmpty()
    }

    /**
     * Delete Object for more efficient app and no memory loss
     */
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}