package com.realworld.io.presentation.editarticle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.realworld.io.R
import com.realworld.io.databinding.FragmentDashBaordBinding
import com.realworld.io.databinding.FragmentEditBinding
import com.realworld.io.model.ArticleModel
import com.realworld.io.presentation.login.LoginViewModel
import com.realworld.io.util.Logger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment() {

    private val viewModel : EditArticleViewModel by viewModels()
    val args: EditFragmentArgs by navArgs()
    private  var _binding: FragmentEditBinding?= null
    private val binding get() = _binding!!

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
        binding.edtBody.setText(args.sampleData?.body.toString())
        binding.edtTitle.setText(args.sampleData?.title.toString())


        binding.updateArticleBtn.setOnClickListener {
            val body = binding.edtBody.text.toString().trim()
            val title = binding.edtTitle.text.toString().trim()
            val id = args.sampleData?.id!!.toInt()

            Logger.d("$body $title title")
            val articleModel = ArticleModel(id, body = body, title =  title)

            viewModel.updateArticle(articleModel)
            findNavController().popBackStack()

        }
        super.onViewCreated(view, savedInstanceState)
    }

}