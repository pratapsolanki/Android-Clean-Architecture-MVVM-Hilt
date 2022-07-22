package com.realworld.io.presentation.addarticle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.realworld.io.R
import com.realworld.io.databinding.FragmentAddArticleBinding
import com.realworld.io.domain.model.ArticleModel
import com.realworld.io.util.TokenManager
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

        val chipGroup = ChipGroup(requireActivity())

        val genres = arrayOf("Thriller", "Comedy", "Adventure")
        for (genre in genres) {
            val chip = Chip(requireActivity())
            chip.text = genre
            chipGroup.addView(chip)
        }

        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autocompleteTV.setAdapter(arrayAdapter)


        binding.addArticleBtn.setOnClickListener {
            val validationResult = checkValidation()
            if(validationResult){
                val body = binding.edtBody.text.toString().trim()
                val title = binding.edtTitle.text.toString().trim()
                val desc = binding.edtDesc.text.toString().trim()
                val username = tokenManager.getName()
                val category = binding.autocompleteTV.text.toString()
                val articleModel = ArticleModel(body = body, title = title , description = desc, username = username, createdAt = Date(), updatedAt = Date(), category = category)
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