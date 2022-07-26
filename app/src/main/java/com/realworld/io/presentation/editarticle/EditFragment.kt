package com.realworld.io.presentation.editarticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.realworld.io.R
import com.realworld.io.databinding.FragmentEditBinding
import com.realworld.io.domain.model.ArticleX
import com.realworld.io.domain.model.Author
import com.realworld.io.presentation.dashboard.ArticleViewModel
import com.realworld.io.util.Logger
import com.realworld.io.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    private val viewModel : ArticleViewModel by viewModels()
    val args: EditFragmentArgs by navArgs()
    private  var _binding: FragmentEditBinding?= null
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
        _binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autocompleteTV.setAdapter(arrayAdapter)
        
        binding.apply {
            args.sampleData?.let {
                edtBody.setText(it.body)
                edtTitle.setText(it.title)
                edtDesc.setText(it.description)
                autocompleteTV.setText(it.tagList.get(0),false)
            }

           updateArticleBtn.setOnClickListener {
                val body = edtBody.text.toString().trim()
                val title = edtTitle.text.toString().trim()
                val desc = edtDesc.text.toString().trim()
                val category = mutableListOf<String>()
               val createDate = args.sampleData?.createdAt.toString()
               val username = tokenManager.getName()
                category.add(autocompleteTV.text.toString().trim())

               val articleModel = ArticleX(body = body, title = title , createdAt = createDate , description = desc, tagList = category, updatedAt = Date().toString(), author = Author("",false,"",username))
                viewModel.updateArticle(articleModel)
                findNavController().popBackStack()

            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}